package com.java.cakerecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.java.cakerecipe.databinding.ActivityCakeDetailBinding;
import com.java.cakerecipe.databinding.ActivityMainBinding;
import com.java.cakerecipe.object.Cake;

public class CakeDetailActivity extends AppCompatActivity {
    private ActivityCakeDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCakeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get intent
        Intent intent = getIntent();
        Cake cake = (Cake) intent.getSerializableExtra("cake");
        binding.txtName.setText(cake.getName());
        binding.description.setText(cake.getDescription());

        byte[] iconImage = cake.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(iconImage, 0, iconImage.length);
        binding.imageView.setImageBitmap(bitmap);


    }
}