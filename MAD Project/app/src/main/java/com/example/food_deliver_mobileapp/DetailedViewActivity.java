package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedViewActivity extends AppCompatActivity {

    private TextView nameView, addressView, cityView, contactView, emailView, openView, closeView;
    private ImageView imageView;
    private DBHandler dbHandler;
    int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        Intent intent = getIntent();


        // Initialize views
        nameView = findViewById(R.id.detailed_name);
        addressView = findViewById(R.id.detailed_address);
        cityView = findViewById(R.id.detailed_city);
        contactView = findViewById(R.id.detailed_contact);
        emailView = findViewById(R.id.detailed_email);
        openView = findViewById(R.id.detailed_open);
        closeView = findViewById(R.id.detailed_close);
        imageView = findViewById(R.id.detailed_image);

        // Initialize database handler
        dbHandler = new DBHandler(this);

        // Get the shop ID passed from ViewActivity
        shopId = getIntent().getIntExtra("shop_id", -1);

        // Load shop details from database
        loadShopDetails(shopId);
    }

    private void loadShopDetails(int shopId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHandler.TABLE_SHOP + " WHERE shop_id=?", new String[]{String.valueOf(shopId)});

        if (cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String city = cursor.getString(3);
            String contact = cursor.getString(4);
            String email = cursor.getString(5);
            String open = cursor.getString(6);
            String close = cursor.getString(7);
            byte[] image = cursor.getBlob(8);

            // Set data to views
            nameView.setText(name);
            addressView.setText(address);
            cityView.setText(city);
            contactView.setText(contact);
            emailView.setText(email);
            openView.setText(open);
            closeView.setText(close);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageView.setImageBitmap(bitmap);
            }
        }

        cursor.close();
        db.close();
    }

    public void addItemsOnClick(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }
    public void viewItemsOnClick(View view){
        Intent intent = new Intent(this, ViewItemActivity.class);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }

    public void editSHopOnClick(View view){
        Intent intent = new Intent(this, EditShopActivity.class);
        intent.putExtra("shop_id", shopId);
        startActivity(intent);
    }

    public void deleteShopOnClick(View view) {

        boolean result = dbHandler.deleteShop(shopId);
        if (result) {
            Toast.makeText(this, "Shop deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete shop", Toast.LENGTH_SHORT).show();
        }
    }

}
