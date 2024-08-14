package com.example.food_deliver_mobileapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;
    private ArrayList<Review> reviewList;
    private DBHandler dbHandler;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // Initialize views
        reviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        ImageView itemImageView = findViewById(R.id.itemImageView);
        TextView itemNameTextView = findViewById(R.id.itemNameTextView);

        dbHandler = new DBHandler(this);

        // Get the item ID from the intent
        Intent intent = getIntent();
        itemId = intent.getIntExtra("itemId", -1);

        if (itemId != -1) {
            // Fetch reviews from the database
            reviewList = new ArrayList<>();
            Cursor cursor = dbHandler.getReviewsByItemId(itemId);

            // Verify that the cursor is not null and contains data
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int reviewId = cursor.getInt(cursor.getColumnIndexOrThrow("review_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                    @SuppressLint("Range") String reviewMessage = cursor.getString(cursor.getColumnIndexOrThrow("review_message"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndexOrThrow("name")); // Ensure this column is in your query
                    reviewList.add(new Review(reviewId, itemId, userId, reviewMessage, username));
                } while (cursor.moveToNext());
            }

            // Setup RecyclerView
            reviewAdapter = new ReviewAdapter(reviewList);
            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            reviewRecyclerView.setAdapter(reviewAdapter);

            // Load item details
            Cursor itemCursor = dbHandler.getItemById(itemId);
            if (itemCursor != null && itemCursor.moveToFirst()) {
                @SuppressLint("Range") String itemName = itemCursor.getString(itemCursor.getColumnIndexOrThrow("item_name"));
                @SuppressLint("Range") byte[] itemImage = itemCursor.getBlob(itemCursor.getColumnIndexOrThrow("item_image"));

                itemNameTextView.setText(itemName);
                itemImageView.setImageBitmap(BitmapFactory.decodeByteArray(itemImage, 0, itemImage.length));
            }
        }
    }

    // Method to retrieve the logged-in user's ID (replace with your actual logic)
    private int getUserId() {
        // Implement your logic to get the current user's ID
        return 1; // Example static user ID (replace with real logic)
    }
}
