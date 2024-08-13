package com.example.food_deliver_mobileapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ItemAnalyticsActivity extends AppCompatActivity {

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_analytics);

        tableLayout = findViewById(R.id.tableLayout);

        // Retrieve and display orders per item
        displayOrdersPerItem();

    }

    private void displayOrdersPerItem() {
        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String ordersPerItemQuery = "SELECT i." + DBHandler.ITEM_NAME_COL + ", COUNT(o." + DBHandler.ORDER_ID_COL + ") AS orders_count, "
                + "SUM(o." + DBHandler.ORDER_TOTAL_AMOUNT_COL + ") AS total_amount "
                + "FROM " + DBHandler.TABLE_ITEM + " i LEFT JOIN " + DBHandler.TABLE_ORDER + " o ON i."
                + DBHandler.ITEM_ID_COL + " = o." + DBHandler.ORDER_ITEM_ID_COL
                + " GROUP BY i." + DBHandler.ITEM_ID_COL
                + " ORDER BY orders_count DESC";

        Cursor cursor = db.rawQuery(ordersPerItemQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndexOrThrow(DBHandler.ITEM_NAME_COL));
                int ordersCount = cursor.getInt(cursor.getColumnIndexOrThrow("orders_count"));
                double totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("total_amount"));

                TableRow tableRow = new TableRow(this);
                TextView itemNameTextView = new TextView(this);
                TextView ordersCountTextView = new TextView(this);
                TextView totalAmountTextView = new TextView(this);

                itemNameTextView.setText(itemName);
                ordersCountTextView.setText(String.valueOf(ordersCount));
                totalAmountTextView.setText(String.format(Locale.getDefault(), "%.2f", totalAmount));

                // Center text in each TextView
                itemNameTextView.setGravity(Gravity.CENTER);
                ordersCountTextView.setGravity(Gravity.CENTER);
                totalAmountTextView.setGravity(Gravity.CENTER);

                tableRow.addView(itemNameTextView);
                tableRow.addView(ordersCountTextView);
                tableRow.addView(totalAmountTextView);

                tableLayout.addView(tableRow);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

}