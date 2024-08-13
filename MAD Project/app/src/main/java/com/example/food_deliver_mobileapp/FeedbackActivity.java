package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    private int orderId, shopId, userId, itemId;
    private EditText feedbackMessage;
    private Button submitFeedbackButton;
    private Spinner categorySpinner;
    private String selectedCategory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackMessage = findViewById(R.id.feedback_message);
        submitFeedbackButton = findViewById(R.id.submit_feedback_button);
        categorySpinner = findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.review_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        //spinner default value
        categorySpinner.setSelection(adapter.getPosition("Shop"));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                selectedCategory = (String) parent.getItemAtPosition(position);

                // Use the selectedCategory as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "Shop";
            }
        });



        // Retrieve the passed data
        Intent intent = getIntent();
        if (intent != null) {
            orderId = intent.getIntExtra("order_id", -1);
            shopId = intent.getIntExtra("shop_id", -1);
            userId = intent.getIntExtra("user_id", -1);
            itemId = intent.getIntExtra("item_id", -1);
        }

        submitFeedbackButton.setOnClickListener(v -> {
            String feedback = feedbackMessage.getText().toString();
            System.out.println("order "+orderId);
            System.out.println("shop "+shopId);
            System.out.println("user "+userId);
            System.out.println("item "+itemId);
            System.out.println("category "+selectedCategory);

            if (!feedback.isEmpty()) {
                DBHandler dbHandler = new DBHandler(FeedbackActivity.this);
                dbHandler.saveFeedback(orderId, userId, shopId, itemId, selectedCategory, feedback);

                Toast.makeText(this, "Feedback submitted for Order ID: " + orderId, Toast.LENGTH_SHORT).show();
                submitFeedbackButton.setEnabled(false);
                finish(); // Close the activity after submission
            } else {
                Toast.makeText(this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


