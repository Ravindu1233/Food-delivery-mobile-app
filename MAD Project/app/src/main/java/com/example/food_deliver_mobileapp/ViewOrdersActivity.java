package com.example.food_deliver_mobileapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> orderList; // Create a list to hold the orders
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders); // Ensure this layout exists

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHandler = new DBHandler(this);
        orderList = new ArrayList<>();

        loadOrders(); // Load orders from the database
    }

    private void loadOrders() {
        Cursor cursor = dbHandler.getAllOrders(); // Create this method in DBHandler

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

                    // Create an Order object and add it to the list
                    Order order = new Order(orderId, shopId, userId, itemId, orderDate, orderState, orderQuantity, orderTotalAmount);
                    orderList.add(order);
                } while (cursor.moveToNext());
            } else {
                Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Database error: Could not retrieve orders", Toast.LENGTH_SHORT).show();
        }

        // Sort the orders: "Pending" orders come first
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if (o1.getOrderState().equals("Pending") && o2.getOrderState().equals("Completed")) {
                    return -1; // o1 comes before o2
                } else if (o1.getOrderState().equals("Completed") && o2.getOrderState().equals("Pending")) {
                    return 1; // o1 comes after o2
                }
                return 0; // no change
            }
        });

        OrderAdapter adapter = new OrderAdapter(orderList, dbHandler);
        recyclerView.setAdapter(adapter);
    }
}
