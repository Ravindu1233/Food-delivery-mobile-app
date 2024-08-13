package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class AddItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEdt, descriptionEdt, priceEdt;
    private Spinner categorySpinner, availabilitySpinner;
    private ImageView coverImageView;
    private Button addItemBtn, uploadImageBtn;
    private DBHandler dbHandler;

    int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_item);

        // Initialize views
        nameEdt = findViewById(R.id.item_name_input);
        descriptionEdt = findViewById(R.id.item_description_input);
        priceEdt = findViewById(R.id.item_price_input);
        categorySpinner = findViewById(R.id.item_category_spinner);
        availabilitySpinner = findViewById(R.id.item_availability_spinner);
        coverImageView = findViewById(R.id.item_cover_image_input);
        uploadImageBtn = findViewById(R.id.upload_image_btn);
        addItemBtn = findViewById(R.id.add_item_btn);

        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", -1);

        // Initialize database handler
        dbHandler = new DBHandler(AddItemActivity.this);

        // Set up the Spinner for Item Category
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up the Spinner for Item Availability
        ArrayAdapter<CharSequence> availabilityAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_availability_options, android.R.layout.simple_spinner_item);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availabilitySpinner.setAdapter(availabilityAdapter);

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdt.getText().toString();
                String description = descriptionEdt.getText().toString();
                String price = priceEdt.getText().toString();
                String category = categorySpinner.getSelectedItem().toString();
                String availability = availabilitySpinner.getSelectedItem().toString();
                byte[] image = imageViewToByte(coverImageView);
                int shop_ID = shopId;

                if (name.isEmpty() || description.isEmpty() || price.isEmpty() || category.isEmpty() || availability.isEmpty()) {
                    Toast.makeText(AddItemActivity.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.addNewItem(name, description, price, category, availability, image, shop_ID);

                Toast.makeText(AddItemActivity.this, "Item has been added", Toast.LENGTH_SHORT).show();

                // Clear inputs
                nameEdt.setText("");
                descriptionEdt.setText("");
                priceEdt.setText("");
                coverImageView.setImageResource(0); // Clear the image view
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            coverImageView.setImageURI(imageUri);
        }
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
