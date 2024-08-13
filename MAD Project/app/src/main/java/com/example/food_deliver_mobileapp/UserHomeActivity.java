package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private TextView locationTextView;

    private String userEmail;
    private DBHandler DBHandler;
    private List<com.example.foodappmadcw04.Shop> shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home_layout);

        welcomeTextView = findViewById(R.id.welcome_text);
        locationTextView = findViewById(R.id.location_text);
        Button profileButton = findViewById(R.id.profile_button);


        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");

        welcomeTextView.setText("Welcome, " + userEmail);

        DBHandler = new DBHandler(this);
        String userCity = getUserCityByEmail(userEmail);
        if (userCity != null) {
            locationTextView.setText(" " + userCity);
        } else {
            locationTextView.setText("Location: Unknown");
        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(UserHomeActivity.this, ProfileActivity.class);
                profileIntent.putExtra("email", userEmail);
                startActivity(profileIntent);
            }
        });

        // Initialize DatabaseHelper and RecyclerView
        DBHandler = new DBHandler(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize shop list and load shops from database
        shopList = new ArrayList<>();
        loadShopsFromDatabase();
        com.example.food_deliver_mobileapp.ShopAdapter shopAdapter = new com.example.food_deliver_mobileapp.ShopAdapter(this, shopList);
        recyclerView.setAdapter(shopAdapter);

        // Set click listeners for category layouts
        setupCategoryClickListeners();
    }

    private void setupCategoryClickListeners() {
        // Find and set click listener for the Breakfast LinearLayout
        View breakfastLayout = findViewById(R.id.linearLayout_breakfast);
        if (breakfastLayout != null) {
            breakfastLayout.setOnClickListener(v -> {
                Intent intent = new Intent(UserHomeActivity.this, com.example.food_deliver_mobileapp.DisplayItemsActivity.class);
                intent.putExtra("category", "Breakfast"); // Pass the category
                intent.putExtra("email", userEmail); // Pass the userEmail
                startActivity(intent);
            });
        } else {
            Log.e("UserHomeActivity", "LinearLayout with ID R.id.linearLayout_breakfast not found in layout");
        }

        // Find and set click listener for the Lunch LinearLayout
        View lunchLayout = findViewById(R.id.linearLayout_lunch);
        if (lunchLayout != null) {
            lunchLayout.setOnClickListener(v -> {
                Intent intent = new Intent(UserHomeActivity.this, com.example.food_deliver_mobileapp.DisplayItemsActivity.class);
                intent.putExtra("category", "Lunch"); // Pass the category
                intent.putExtra("email", userEmail);// Pass the userEmail
                startActivity(intent);
            });
        } else {
            Log.e("UserHomeActivity", "LinearLayout with ID R.id.linearLayout_lunch not found in layout");
        }

        // Find and set click listener for the Dinner LinearLayout
        View dinnerLayout = findViewById(R.id.linearLayout_dinner);
        if (dinnerLayout != null) {
            dinnerLayout.setOnClickListener(v -> {
                Intent intent = new Intent(UserHomeActivity.this, com.example.food_deliver_mobileapp.DisplayItemsActivity.class);
                intent.putExtra("category", "Dinner"); // Pass the category
                intent.putExtra("email", userEmail);// Pass the userEmail
                startActivity(intent);
            });
        } else {
            Log.e("UserHomeActivity", "LinearLayout with ID R.id.linearLayout_dinner not found in layout");
        }
    }

    private void loadShopsFromDatabase() {
        Cursor cursor = DBHandler.getAllShops(); // Method to fetch all shops
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int nameIndex = cursor.getColumnIndex("shop_name");
                    int locationIndex = cursor.getColumnIndex("shop_city");
                    int imageIndex = cursor.getColumnIndex("shop_image");

                    // Check if column indices are valid
                    if (nameIndex != -1 && locationIndex != -1 && imageIndex != -1) {
                        String name = cursor.getString(nameIndex);
                        String location = cursor.getString(locationIndex);
                        byte[] imageBytes = cursor.getBlob(imageIndex);
                        String rating = "4.8 ★"; // Hardcoded for demonstration; you might want to fetch this from the database
                        shopList.add(new com.example.foodappmadcw04.Shop(name, location, imageBytes, rating));
                    } else {
                        Log.e("UserHomeActivity", "Column index is -1, check column names in database schema.");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        } else {
            Log.e("UserHomeActivity", "Cursor is null, check database query.");
        }
    }

    private String getUserCityByEmail(String email) {
        Cursor cursor = DBHandler.getUserByEmail(email);
        if (cursor != null && cursor.moveToFirst()) {
            int cityIndex = cursor.getColumnIndex("city");
            if (cityIndex != -1) {
                String city = cursor.getString(cityIndex);
                cursor.close();
                return city;
            }
            cursor.close();
        }
        return null;
    }

    // Helper method to convert Drawable to byte[]
    private byte[] drawableToByteArray(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return getBitmapAsByteArray(bitmap);
    }



    // Helper method to convert Bitmap to byte[]
    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    // Helper method to convert byte[] to Bitmap
    private Bitmap getBitmapFromByteArray(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
