package com.example.food_deliver_mobileapp;

import static com.example.food_deliver_mobileapp.DBHandler.TABLE_SHOP;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditShopActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEdt, addressEdt, cityEdt, contactEdt, emailEdt, openEdt, closeEdt;
    private ImageView coverImageView;
    private Button editShopBtn, uploadImageBtn;
    private DBHandler dbHandler;
    private Bitmap selectedImageBitmap;
    int shopID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_shop);

        dbHandler = new DBHandler(this);

        nameEdt = findViewById(R.id.edit_shop_name_input);
        addressEdt = findViewById(R.id.edit_shop_address_input);
        cityEdt = findViewById(R.id.edit_shop_city_input);
        contactEdt = findViewById(R.id.edit_shop_contact_input);
        emailEdt = findViewById(R.id.edit_shop_email_input);
        openEdt = findViewById(R.id.edit_shop_open_input);
        closeEdt = findViewById(R.id.edit_shop_close_input);
        coverImageView = findViewById(R.id.edit_shop_cover_image_input);
        uploadImageBtn = findViewById(R.id.edit_shop_upload_image_btn);
        editShopBtn = findViewById(R.id.edit_shop_btn);

        Intent intent = getIntent();
        shopID = intent.getIntExtra("shop_id", -1);

        fetchItemDetails();

        uploadImageBtn.setOnClickListener(v -> openGallery());

        editShopBtn.setOnClickListener(v -> {
            String name = nameEdt.getText().toString();
            String address = addressEdt.getText().toString();
            String city = cityEdt.getText().toString();
            String contact = contactEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String open = openEdt.getText().toString();
            String close = closeEdt.getText().toString();


            byte[] image = selectedImageBitmap != null ? getImageBytes(selectedImageBitmap) : null; // Use the selected image or null if no image is selected

            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || contact.isEmpty() || email.isEmpty() || open.isEmpty() || close.isEmpty()) {
                Toast.makeText(EditShopActivity.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isUpdated = dbHandler.updateShop(shopID, name, address, city, contact, email, open, close, image);

            if (isUpdated) {
                Toast.makeText(EditShopActivity.this, "Item has been updated", Toast.LENGTH_SHORT).show();

                finish();
            } else {
                Toast.makeText(EditShopActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchItemDetails() {
        sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_SHOP + " WHERE shop_id=?", new String[]{String.valueOf(shopID)});
        if (cursor.moveToFirst()) {
            nameEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_name")));
            addressEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_address")));
            cityEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_city")));
            contactEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_contact")));
            emailEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_email")));
            openEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_open")));
            closeEdt.setText(cursor.getString(cursor.getColumnIndexOrThrow("shop_close")));

            // Retrieve and display image
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("shop_image"));
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                coverImageView.setImageBitmap(bitmap);
            }
        }
        cursor.close();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                selectedImageBitmap = BitmapFactory.decodeStream(inputStream);
                coverImageView.setImageBitmap(selectedImageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }
}