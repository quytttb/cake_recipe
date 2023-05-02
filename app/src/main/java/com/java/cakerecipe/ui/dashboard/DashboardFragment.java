package com.java.cakerecipe.ui.dashboard;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.java.cakerecipe.AddCakeActivity;
import com.java.cakerecipe.AddCustomerActivity;
import com.java.cakerecipe.adapter.CustomerAdapter;
import com.java.cakerecipe.database.CustomerDatabase;
import com.java.cakerecipe.databinding.FragmentDashboardBinding;
import com.java.cakerecipe.object.Cake;
import com.java.cakerecipe.object.Customer;

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentDashboardBinding binding;

    public static final String DATABASE_NAME = "CUSTOMER.db";
    private static final int DATABASE_VERSION = 1;

    // Initialize database
    CustomerDatabase customerDatabase;

    // Initialize adapter
    CustomerAdapter customerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        customerDatabase = new CustomerDatabase(requireContext(), DATABASE_NAME, null, DATABASE_VERSION);

        Cursor cursor = customerDatabase.readAllData();
        cursor.moveToFirst();
        ArrayList<Customer> customerArrayList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            customerArrayList.add(new Customer(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            cursor.moveToNext();
        }
        cursor.close();

        // Set up the list view
        // Adapter for the list view
        customerAdapter = new CustomerAdapter(requireContext(), customerArrayList);
        binding.listViewCustomer.setAdapter(customerAdapter);

        binding.listViewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO
            }
        });

        binding.listViewCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //show the dialog delete this cake
                Customer customer = (Customer) parent.getItemAtPosition(position);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setTitle("Delete this cake?");
                builder.setMessage("Are you sure you want to delete this cake?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    customerDatabase.delete(position + 1);
                    customerAdapter.remove(customer);
                    customerAdapter.notifyDataSetChanged();
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.show();
                return false;
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // Get the cake that was added
                Customer customer = (Customer) data.getSerializableExtra("customer");
                //update database
                customerDatabase.insert(customer.getName(), customer.getPhone(), customer.getRecipeName(), customer.getDateTime());

                // Add the cake to the list and update the adapter
                binding.listViewCustomer.getAdapter();
                customerAdapter.add(customer);
                customerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(com.java.cakerecipe.R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent = new Intent(requireContext(), AddCustomerActivity.class);
        startActivityForResult(intent, 2);
        return false;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}