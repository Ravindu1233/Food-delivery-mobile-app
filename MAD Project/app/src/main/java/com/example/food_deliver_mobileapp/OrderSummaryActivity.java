package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    private TextView itemIdTextView;
    private TextView itemNameTextView;
    private TextView itemDescriptionTextView;
    private TextView itemPriceTextView;
    private TextView quantityTextView;
    private TextView promotionIdTextView;
    private TextView promotionCodeTextView;
    private TextView totalAmountTextView;
    private TextView discountAmountTextView;
    private TextView orderAmountTextView;
    private TextView shopIdTextView;
    private TextView userIdTextView;
    private TextView orderDateTextView;
    private Button confirmButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        dbHandler = new DBHandler(OrderSummaryActivity.this);

        // Initialize views
        itemIdTextView = findViewById(R.id.order_item_id);
        itemNameTextView = findViewById(R.id.order_item_name);
        itemDescriptionTextView = findViewById(R.id.order_item_description);
        itemPriceTextView = findViewById(R.id.order_item_price);
        quantityTextView = findViewById(R.id.order_quantity);
        promotionIdTextView = findViewById(R.id.promotion_id);
        promotionCodeTextView = findViewById(R.id.order_promotion_code);
        orderAmountTextView = findViewById(R.id.order_amount);
        discountAmountTextView = findViewById(R.id.discount_amount);
        totalAmountTextView = findViewById(R.id.order_total_amount);
        shopIdTextView = findViewById(R.id.order_shop_id);
        userIdTextView = findViewById(R.id.order_user_id);
        orderDateTextView = findViewById(R.id.order_date);
        confirmButton = findViewById(R.id.btn_confirm);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        int item_id = intent.getIntExtra("item_id", -1);
        String item_name = intent.getStringExtra("item_name");
        String item_description = intent.getStringExtra("item_description");
        String item_price = intent.getStringExtra("item_price");
        int order_quantity = intent.getIntExtra("quantity", 0);
        int promotion_id = intent.getIntExtra("promotion_id",-1);
        String promotion_code = intent.getStringExtra("promotion_code");
        String order_date = intent.getStringExtra("order_date");
        int user_id = intent.getIntExtra("user_id", -1);
        double order_amount = intent.getDoubleExtra("order_amount", 0.0);
        double order_discount_amount = intent.getDoubleExtra("discount_amount", 0.0);
        double order_total_amount = intent.getDoubleExtra("total_amount", 0.0);
        int shop_id = intent.getIntExtra("shop_id",-1);
        String order_state = "Pending";

        // Set the data to the TextViews
        itemIdTextView.setText("Item ID: " + item_id);
        itemNameTextView.setText("Item Name: " + item_name);
        itemDescriptionTextView.setText("Item Description: " + item_description);
        itemPriceTextView.setText("Item Price: " + item_price);
        quantityTextView.setText("Quantity: " + order_quantity);
        orderDateTextView.setText("Order Date: " +order_date);
        if(promotion_id>=1) {
            promotionIdTextView.setText("Promotion Id: " + promotion_id);
            promotionCodeTextView.setText("Promotion Code: " + promotion_code);
        }
        orderAmountTextView.setText("Order Amount: LKR " + String.format("%.2f", order_amount));
        discountAmountTextView.setText("Discount Amount: LKR " + String.format("%.2f", order_discount_amount));
        totalAmountTextView.setText("Total Amount: LKR " + String.format("%.2f", order_total_amount));
        shopIdTextView.setText("Shop ID: " + shop_id);
        userIdTextView.setText("User ID: " + user_id);
        confirmButton.setText("Confirm Buy: LKR" +order_total_amount);


        System.out.println(shop_id);
        System.out.println(user_id);
        System.out.println(item_id);
        System.out.println(order_date);
        System.out.println(order_state);
        System.out.println(promotion_id);
        System.out.println(order_quantity);
        System.out.println(order_amount);
        System.out.println(order_discount_amount);
        System.out.println(order_total_amount);

        NotificationUtils.createNotificationChannel(this);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.addNewOrder(shop_id, user_id,item_id,order_date,order_state,promotion_id,order_quantity,order_amount,order_discount_amount,order_total_amount);

                Toast.makeText(OrderSummaryActivity.this, "Order has been added", Toast.LENGTH_SHORT).show();
                confirmButton.setEnabled(false);
                NotificationUtils.sendNotification(
                        OrderSummaryActivity.this,
                        "Order Placed",
                        "Your order has been placed successfully."
                );




            }
        });






    }
}

