package com.example.food_deliver_mobileapp;

import static com.example.food_deliver_mobileapp.DBHandler.TABLE_ITEM;

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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditItemActivity extends AppCompatActivity {

    DBHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    EditText itemName, itemDescription, itemPrice;
    Spinner itemCategory, itemAvailability;
    ImageView itemImage;
    Button btnEditUploadImage,btnEditUpdate;
    int itemId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Bitmap selectedImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        dbHandler = new DBHandler(this);

        itemName = findViewById(R.id.edit_item_name);
        itemDescription = findViewById(R.id.edit_item_description);
        itemPrice = findViewById(R.id.edit_item_price);
        itemCategory = findViewById(R.id.edit_item_category);
        itemAvailability = findViewById(R.id.edit_item_availability);
        itemImage = findViewById(R.id.edit_item_image);
        btnEditUploadImage = findViewById(R.id.btn_edit_upload_image);
        btnEditUpdate = findViewById(R.id.btn_edit_update);

        // Setting up the itemCategory Spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(categoryAdapter);

        // Setting up the itemAvailability Spinner
        ArrayAdapter<CharSequence> availabilityAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_availability_options, android.R.layout.simple_spinner_item);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemAvailability.setAdapter(availabilityAdapter);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("item_id", -1);

        fetchItemDetails();

        btnEditUploadImage.setOnClickListener(v -> openGallery());

        btnEditUpdate.setOnClickListener(v -> {
            String name = itemName.getText().toString();
            String description = itemDescription.getText().toString();
            String price = itemPrice.getText().toString();
            String category = itemCategory.getSelectedItem().toString();
            String availability = itemAvailability.getSelectedItem().toString();
            byte[] image = selectedImageBitmap != null ? getImageBytes(selectedImageBitmap) : null; // Use the selected image or null if no image is selected

            if (name.isEmpty() || description.isEmpty() || price.isEmpty() || category.isEmpty() || availability.isEmpty()) {
                Toast.makeText(EditItemActivity.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update the item in the database
            boolean isUpdated = dbHandler.updateItem(itemId, name, description, price, category, availability, image);

            if (isUpdated) {
                Toast.makeText(EditItemActivity.this, "Item has been updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditItemActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchItemDetails() {
        sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ITEM + " WHERE item_id=?", new String[]{String.valueOf(itemId)});
        if (cursor.moveToFirst()) {
            itemName.setText(cursor.getString(cursor.getColumnIndexOrThrow("item_name")));
            itemDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow("item_description")));
            itemPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("item_price")));

            // Set spinner selection based on fetched data
            ArrayAdapter<CharSequence> categoryAdapter = (ArrayAdapter<CharSequence>) itemCategory.getAdapter();
            ArrayAdapter<CharSequence> availabilityAdapter = (ArrayAdapter<CharSequence>) itemAvailability.getAdapter();

            itemCategory.setSelection(categoryAdapter.getPosition(cursor.getString(cursor.getColumnIndexOrThrow("item_category"))));
            itemAvailability.setSelection(availabilityAdapter.getPosition(cursor.getString(cursor.getColumnIndexOrThrow("item_availability"))));

            // Retrieve and display image
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("item_image"));
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                itemImage.setImageBitmap(bitmap);
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
                itemImage.setImageBitmap(selectedImageBitmap);
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
