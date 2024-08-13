package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameTextView, emailTextView, phoneTextView, addressTextView, cityTextView;
    private ImageView profileImageView;
    private Button editAccountButton;
    private DBHandler dbHandler;
    private Button logoutButton;
    private Button viewOrderButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        nameTextView = findViewById(R.id.profile_name);
        emailTextView = findViewById(R.id.profile_email);
        phoneTextView = findViewById(R.id.profile_phone);
        addressTextView = findViewById(R.id.profile_address);
        cityTextView = findViewById(R.id.profile_city);
        profileImageView = findViewById(R.id.profile_image);
        editAccountButton = findViewById(R.id.edit_account_button);
        viewOrderButton = findViewById(R.id.vieworder_button);

        logoutButton = findViewById(R.id.logout_button);


        dbHandler = new DBHandler(this);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Cursor cursor = dbHandler.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            emailTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            phoneTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            addressTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("address")));
            cityTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow("city")));

            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileImageView.setImageBitmap(bitmap);
            }
            cursor.close();
        }

        editAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                editIntent.putExtra("email", email);
                startActivity(editIntent);
            }
        });




        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(logoutIntent);
                finish(); // Optional: close the current activity to prevent the user from going back to it
            }
        });

        viewOrderButton = findViewById(R.id.vieworder_button);

        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewOrdersIntent = new Intent(ProfileActivity.this, UserOrderActivity.class);
                viewOrdersIntent.putExtra("email", email);
                startActivity(viewOrdersIntent);
            }
        });


    }

}
