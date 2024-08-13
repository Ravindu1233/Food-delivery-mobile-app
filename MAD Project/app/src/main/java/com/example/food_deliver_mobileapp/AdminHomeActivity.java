package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private Button profileButton;
    private Button viewOrdersButton; // Button for viewing orders
    private String adminEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        welcomeTextView = findViewById(R.id.welcome_text_view);
        profileButton = findViewById(R.id.admin_profile_button);
        viewOrdersButton = findViewById(R.id.view_all_orders_btn); // Initialize view orders button

        Intent intent = getIntent();
        adminEmail = intent.getStringExtra("email");  // Get the email from the intent

        if (adminEmail != null) {
            welcomeTextView.setText("Welcome, " + adminEmail);
        } else {
            welcomeTextView.setText("Welcome, Admin");
        }

        profileButton.setOnClickListener(v -> {
            Intent profileIntent = new Intent(AdminHomeActivity.this, AdminProfileActivity.class);
            profileIntent.putExtra("email", adminEmail);
            startActivity(profileIntent);
        });


        viewOrdersButton.setOnClickListener(v -> {
            Intent viewOrdersIntent = new Intent(AdminHomeActivity.this, ViewOrdersActivity.class); // Change the variable name here
            startActivity(viewOrdersIntent);
        });
    }

    public void addNewOnClick(View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void viewShopsOnClick(View view){
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity(intent);
    }

    public void addPromotionOnClick(View view){
        Intent intent = new Intent(this, AdminAddPromotionActivity.class);
        startActivity(intent);
    }

    public void analyticsPanelClick(View view){
        Intent intent = new Intent(this, AnalyticsActiviy.class);
        startActivity(intent);
    }

}
