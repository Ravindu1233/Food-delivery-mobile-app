package com.example.food_deliver_mobileapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserOrderAdapter userOrderAdapter;
    private ArrayList<Order> userOrderList;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

        recyclerView = findViewById(R.id.user_orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DBHandler(this);
        userOrderList = new ArrayList<>();

        String email = getIntent().getStringExtra("email");
        loadUserOrders(email);
    }

    private void loadUserOrders(String email) {
        Cursor cursor = dbHandler.getOrdersByUserEmail(email); // Create this method in DBHandler

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("order_id"));
                    @SuppressLint("Range") int shopId = cursor.getInt(cursor.getColumnIndex("shop_id"));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                    @SuppressLint("Range") int itemId = cursor.getInt(cursor.getColumnIndex("item_id"));
                    @SuppressLint("Range") String orderDate = cursor.getString(cursor.getColumnIndex("order_date"));
                    @SuppressLint("Range") String orderState = cursor.getString(cursor.getColumnIndex("order_states"));
                    @SuppressLint("Range") int orderQuantity = cursor.getInt(cursor.getColumnIndex("order_quantity"));
                    @SuppressLint("Range") double orderTotalAmount = cursor.getDouble(cursor.getColumnIndex("order_total_amount"));

                    Order order = new Order(orderId, shopId, userId, itemId, orderDate, orderState, orderQuantity, orderTotalAmount);
                    userOrderList.add(order);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Database error: Could not retrieve orders", Toast.LENGTH_SHORT).show();
        }

        userOrderAdapter = new UserOrderAdapter(userOrderList);
        recyclerView.setAdapter(userOrderAdapter);
    }
}

