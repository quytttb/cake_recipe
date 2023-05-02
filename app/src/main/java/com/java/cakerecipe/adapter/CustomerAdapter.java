package com.java.cakerecipe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.java.cakerecipe.R;
import com.java.cakerecipe.object.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    public CustomerAdapter(Context context, ArrayList<Customer> customers) {
        super(context, 0, customers);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_customer, parent, false);
        }

        Customer customer = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.customer_name);
        nameTextView.setText(customer.getName());

        TextView phoneTextView = convertView.findViewById(R.id.customer_phone);
        phoneTextView.setText(customer.getPhone());

        TextView recipeNameTextView = convertView.findViewById(R.id.customer_recipe_name);
        recipeNameTextView.setText(customer.getRecipeName());

        TextView dateTimeTextView = convertView.findViewById(R.id.customer_date_time);
        dateTimeTextView.setText(customer.getDateTime());
        return convertView;
    }


}
