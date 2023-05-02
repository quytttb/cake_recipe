package com.java.cakerecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.java.cakerecipe.databinding.ActivityAddCakeBinding;
import com.java.cakerecipe.object.Cake;
import com.java.cakerecipe.object.Customer;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCakeActivity extends AppCompatActivity {

    ActivityAddCakeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCakeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set up the image view
        binding.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the gallery
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        // Set up the OK button
        binding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Get the name and description from the edit text fields
                    String name = binding.name.getText().toString();
                    String description = binding.description.getText().toString();

                    //get image from image view
                    byte[] image = ImageView_To_Byte(binding.imageView);

                    // Create a new cake with the name and description
                    Cake cake = new Cake(image, name, description);

                    // Return the new cake to Activity 1
                    Intent intent = new Intent();
                    intent.putExtra("cake", cake);
                    setResult(RESULT_OK, intent);

                    finish();
            }
        });

        // Set up the Cancel button
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //convert image to byte
    public byte[] ImageView_To_Byte(ImageView image) {
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();

    }

    //set the image view to the selected image
    @Override
    protected void onActivityResult(int requestCodeForImage, int resultCode, Intent data) {
        super.onActivityResult(requestCodeForImage, resultCode, data);
        // Check if the request code is the same as what is passed in
        if (requestCodeForImage == 1) {
            // Check if the result code is OK
            if (resultCode == RESULT_OK) {
                // Get the image from the data
                binding.imageView.setImageURI(data.getData());
            }
        }
    }

}