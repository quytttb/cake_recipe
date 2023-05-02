package com.java.cakerecipe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.java.cakerecipe.R;
import com.java.cakerecipe.object.Cake;

import java.util.ArrayList;

public class CakeAdapter extends ArrayAdapter<Cake> {
    public CakeAdapter(Context context, ArrayList<Cake> cakes) {
        super(context, 0, cakes);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cake, parent, false);
        }

        Cake cake = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.item_name);
        nameTextView.setText(cake.getName());

        TextView descriptionTextView = convertView.findViewById(R.id.item_description);
        descriptionTextView.setText(cake.getDescription());

        ImageView cakeImageView = convertView.findViewById(R.id.item_image);
        byte[] iconImage = cake.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(iconImage, 0, iconImage.length);
        cakeImageView.setImageBitmap(bitmap);


        return convertView;
    }
}
