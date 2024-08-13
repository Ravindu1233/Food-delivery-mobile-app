package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderDetailsActivity extends AppCompatActivity {

    private EditText quantityEditText;
    private EditText promotionCodeEditText;
    private Button orderButton;
    private TextView itemIdTextView;
    private TextView itemNameTextView;
    private TextView itemDescriptionTextView;
    private TextView itemPriceTextView;
    private TextView shopIdTextView;
    private TextView orderDateTextView;
    private TextView userEmailTextView;
    private TextView userIdTextView;

    private int itemId;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private int shopId;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        // Initialize views
        userEmailTextView = findViewById(R.id.user_email);
        userIdTextView = findViewById(R.id.user_id);
        itemIdTextView = findViewById(R.id.item_id);
        itemNameTextView = findViewById(R.id.item_name);
        itemDescriptionTextView = findViewById(R.id.item_description);
        itemPriceTextView = findViewById(R.id.item_price);
        shopIdTextView = findViewById(R.id.shop_id);
        orderDateTextView = findViewById(R.id.order_date);
        quantityEditText = findViewById(R.id.quantity);
        promotionCodeEditText = findViewById(R.id.promotion_code);
        orderButton = findViewById(R.id.to_order_button);

        // Retrieve data passed via the intent
        itemId = getIntent().getIntExtra("item_id", -1);
        itemName = getIntent().getStringExtra("item_name");
        itemDescription = getIntent().getStringExtra("item_description");
        itemPrice = getIntent().getStringExtra("item_price");
        shopId = getIntent().getIntExtra("shop_id",-1);
        userEmail = getIntent().getStringExtra("email");

        // Display the userEmail
        if (userEmailTextView != null && userEmail != null) {
            userEmailTextView.setText("User Email: " + userEmail);
        }

        // Fetch shop_id from database if needed
        if (itemId != -1) {
            DBHandler dbHandler = new DBHandler(this);
            shopId = Integer.parseInt(dbHandler.getShopIdByItemId(itemId));
        }

        DBHandler dbHandler = new DBHandler(this);
        int userId = dbHandler.getUserIdByEmail(userEmail);
        userIdTextView.setText("User ID: " + userId);

        // Set the retrieved data to the corresponding TextViews
        //userIdTextView.setText(userEmail);
        itemIdTextView.setText("Item ID: " + itemId);
        itemNameTextView.setText(itemName);
        itemDescriptionTextView.setText(itemDescription);
        itemPriceTextView.setText(itemPrice);
        shopIdTextView.setText("Shop ID: " + shopId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        orderDateTextView.setText("Order Date: " + currentDate);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }

    private void placeOrder() {
        // Get user inputs
        String quantityText = quantityEditText.getText().toString();
        String promotionCode = promotionCodeEditText.getText().toString();
        int promotionId = -1;

        // Check if quantity is valid
        if (quantityText.isEmpty()) {
            quantityEditText.setError("Quantity is required");
            return;
        }

        int itemQuantity;
        try {
            itemQuantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            quantityEditText.setError("Invalid quantity");
            return;
        }

        // Extract item price and calculate total amount
        double itemPriceValue;
        try {
            itemPriceValue = Double.parseDouble(itemPrice.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            itemPriceValue = 0;
        }

        double orderAmount = itemPriceValue * itemQuantity;
        double totalAmount = orderAmount;

        // Apply discount if promotion code is "p10"
        if ("p10".equalsIgnoreCase(promotionCode)) {
            totalAmount = orderAmount*0.9; // Apply 10% discount
            promotionId = 1;
        } else if ("p15".equalsIgnoreCase(promotionCode)) {
            totalAmount = orderAmount*0.85; // Apply 15% discount
            promotionId = 2;
        } else if ("p20".equalsIgnoreCase(promotionCode)) {
            totalAmount = orderAmount*0.8; // Apply 20% discount
            promotionId = 3;
        }
        double discountAmount = orderAmount - totalAmount;



        // Get user ID from database
        DBHandler dbHandler = new DBHandler(this);
        int userId = dbHandler.getUserIdByEmail(userEmail);

        // Create Intent to start the new activity
        Intent intent = new Intent(OrderDetailsActivity.this, OrderSummaryActivity.class);
        intent.putExtra("item_id", itemId);
        intent.putExtra("item_name", itemName);
        intent.putExtra("item_description", itemDescription);
        intent.putExtra("item_price", itemPrice);
        intent.putExtra("shop_id", shopId);
        intent.putExtra("order_date", orderDateTextView.getText().toString());
        intent.putExtra("quantity", itemQuantity);
        intent.putExtra("promotion_id", promotionId);
        intent.putExtra("promotion_code", promotionCode);
        intent.putExtra("user_id", userId);
        intent.putExtra("order_amount", orderAmount);
        intent.putExtra("discount_amount", discountAmount);
        intent.putExtra("total_amount", totalAmount);
        startActivity(intent);


        //System.out.println("Total Amount: " + totalAmount);
        //System.out.println("Promotion Code: " + promotionCode);
       //System.out.println("Shop ID: " + shopId);
    }



}

