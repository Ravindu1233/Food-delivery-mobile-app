package com.example.food_deliver_mobileapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrdersPerCustomerActivity extends AppCompatActivity {

    private TableLayout ordersTable;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_per_customer);

        ordersTable = findViewById(R.id.ordersTable);
        dbHandler = new DBHandler(this);

        // Fetch data from the database
        Cursor cursor = dbHandler.getTotalOrdersPerCustomer();

        // Check if data exists
        if (cursor.moveToFirst()) {
            do {
                // Create a new table row
                TableRow row = new TableRow(this);

                // Get data from the cursor
                //String userId = cursor.getString(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String totalQuantity = cursor.getString(3);
                String totalAmount = cursor.getString(4);

                /* Create TextViews for each column and add them to the row
                TextView userIdTextView = new TextView(this);
                userIdTextView.setText(userId);
                userIdTextView.setPadding(4, 4, 4, 4);
                row.addView(userIdTextView);*/

                TextView userNameTextView = new TextView(this);
                userNameTextView.setText(userName);
                userNameTextView.setPadding(4, 4, 4, 4);
                row.addView(userNameTextView);

                TextView userEmailTextView = new TextView(this);
                userEmailTextView.setText(userEmail);
                userEmailTextView.setPadding(4, 4, 4, 4);
                row.addView(userEmailTextView);

                TextView totalQuantityTextView = new TextView(this);
                totalQuantityTextView.setText(totalQuantity);
                totalQuantityTextView.setPadding(4, 4, 4, 4);
                row.addView(totalQuantityTextView);

                TextView totalAmountTextView = new TextView(this);
                totalAmountTextView.setText(totalAmount);
                totalAmountTextView.setPadding(4, 4, 4, 4);
                row.addView(totalAmountTextView);

                // Add the row to the table layout
                ordersTable.addView(row);

            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();
    }

}
