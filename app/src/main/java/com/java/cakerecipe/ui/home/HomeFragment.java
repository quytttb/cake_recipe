package com.java.cakerecipe.ui.home;

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
import com.java.cakerecipe.CakeDetailActivity;
import com.java.cakerecipe.adapter.CakeAdapter;
import com.java.cakerecipe.database.CakeDatabase;
import com.java.cakerecipe.databinding.FragmentHomeBinding;
import com.java.cakerecipe.object.Cake;

import java.util.ArrayList;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;

    public static final String DATABASE_NAME = "CAKE.db";
    private static final int DATABASE_VERSION = 1;

    // Initialize database
    CakeDatabase cakeDatabase;

    // Initialize database
    CakeAdapter cakeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cakeDatabase = new CakeDatabase(requireContext(), DATABASE_NAME, null, DATABASE_VERSION);

        //cursor là con trỏ trỏ đến dữ liệu trong database
        Cursor cursor = cakeDatabase.ReadAllData();
        cursor.moveToFirst();
        ArrayList<Cake> cakeArrayList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            cakeArrayList.add(new Cake(cursor.getBlob(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();

        // Set up the list view
        // Adapter for the list view
        cakeAdapter = new CakeAdapter(requireContext(), cakeArrayList);
        binding.listViewCake.setAdapter(cakeAdapter);

        binding.listViewCake.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cake cake = (Cake) parent.getItemAtPosition(position);
                Intent intent = new Intent(requireContext(), CakeDetailActivity.class);
                intent.putExtra("cake", cake);
                startActivity(intent);
            }
        });

        binding.listViewCake.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //show the dialog delete this cake
                Cake cake = (Cake) parent.getItemAtPosition(position);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
                builder.setTitle("Delete this cake?");
                builder.setMessage("Are you sure you want to delete this cake?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    cakeDatabase.Delete(String.valueOf(position + 1));
                    cakeAdapter.remove(cake);
                    cakeAdapter.notifyDataSetChanged();
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.show();
                return false;
            }
        });

        return root;
    }

    // This method is called when the user comes back from Activity Add after adding a cake
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Get the cake that was added
                Cake cake = (Cake) data.getSerializableExtra("cake");
                //update database
                cakeDatabase.insert(cake.getImage(), cake.getName(), cake.getDescription());

                // Add the cake to the list and update the adapter
                binding.listViewCake.getAdapter();
                cakeAdapter.add(cake);
                cakeAdapter.notifyDataSetChanged();
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
        Intent intent = new Intent(requireContext(), AddCakeActivity.class);
        startActivityForResult(intent, 1);
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}