package com.example.food_deliver_mobileapp;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class AddActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText nameEdt, addressEdt, cityEdt, contactEdt, emailEdt, openEdt, closeEdt;
    private ImageView coverImageView;
    private Button addShopBtn, uploadImageBtn;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        nameEdt = findViewById(R.id.name_input);
        addressEdt = findViewById(R.id.address_input);
        cityEdt = findViewById(R.id.city_input);
        contactEdt = findViewById(R.id.contact_input);
        emailEdt = findViewById(R.id.email_input);
        openEdt = findViewById(R.id.open_input);
        closeEdt = findViewById(R.id.close_input);
        coverImageView = findViewById(R.id.cover_image_input);
        uploadImageBtn = findViewById(R.id.upload_image_btn);
        addShopBtn = findViewById(R.id.add_shop_btn);

        dbHandler = new DBHandler(AddActivity.this);

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String city = cityEdt.getText().toString();
                String contact = contactEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String open = openEdt.getText().toString();
                String close = closeEdt.getText().toString();
                byte[] image = imageViewToByte(coverImageView);

                if (name.isEmpty() || address.isEmpty() || city.isEmpty() || contact.isEmpty() || email.isEmpty() || open.isEmpty() || close.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please enter all the data", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.addNewShop(name, address, city, contact, email, open, close, image);

                Toast.makeText(AddActivity.this, "Shop has been added", Toast.LENGTH_SHORT).show();

                // Clear inputs
                nameEdt.setText("");
                addressEdt.setText("");
                cityEdt.setText("");
                contactEdt.setText("");
                emailEdt.setText("");
                openEdt.setText("");
                closeEdt.setText("");
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

