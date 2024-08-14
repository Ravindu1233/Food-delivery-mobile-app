package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnalyticsActiviy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analytics_activiy);

    }

    public void itemAnalyticsOnClick(View view){
        Intent intent = new Intent(this, ItemAnalyticsActivity.class);
        startActivity(intent);
        // Find the button by its ID
        Button ordersPerCustomerButton = findViewById(R.id.orders_per_customer_btn);

        // Set an onClickListener for the button
        ordersPerCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the OrdersPerCustomerActivity
                Intent intent = new Intent(AnalyticsActiviy.this, OrdersPerCustomerActivity.class);
                startActivity(intent);
            }
        });

    }
}