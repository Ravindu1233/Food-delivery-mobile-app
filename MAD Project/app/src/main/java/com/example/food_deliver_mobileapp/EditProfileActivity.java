package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private ImageView profileImageView;
    private Button selectImageButton, saveButton;
    private DBHandler dbHandler;
    private String email;
    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);

        nameEditText = findViewById(R.id.edit_profile_name);
        phoneEditText = findViewById(R.id.edit_profile_phone);
        addressEditText = findViewById(R.id.edit_profile_address);
        cityEditText = findViewById(R.id.edit_profile_city);
        profileImageView = findViewById(R.id.edit_profile_image);
        selectImageButton = findViewById(R.id.select_image_button);
        saveButton = findViewById(R.id.save_profile_button);

        dbHandler = new DBHandler(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Cursor cursor = dbHandler.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            phoneEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            addressEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            cityEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("city")));

            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileImageView.setImageBitmap(bitmap);
            }
            cursor.close();
        }

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String city = cityEditText.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!name.matches("[a-zA-Z ]+")) {
                    Toast.makeText(EditProfileActivity.this, "Name can only contain letters", Toast.LENGTH_SHORT).show();
                } else if (!phone.matches("\\d{10}")) {
                    Toast.makeText(EditProfileActivity.this, "Phone number must be exactly 10 digits", Toast.LENGTH_SHORT).show();
                } else if (!city.matches("[a-zA-Z ]+")) {
                    Toast.makeText(EditProfileActivity.this, "City can only contain letters", Toast.LENGTH_SHORT).show();
                } else {
                    dbHandler.updateUser(name, phone, address, city, selectedBitmap, email);

                    Intent profileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    profileIntent.putExtra("email", email);
                    startActivity(profileIntent);
                    finish();
                }
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profileImageView.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
