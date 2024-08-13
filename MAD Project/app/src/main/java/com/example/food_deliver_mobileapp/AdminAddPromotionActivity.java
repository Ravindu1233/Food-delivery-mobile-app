package com.example.food_deliver_mobileapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminAddPromotionActivity extends AppCompatActivity {
    private EditText promotionNameEditText, promotionDescriptionEditText, promotionPercentageEditText;
    private Button savePromotionButton;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_promotion);

        promotionNameEditText = findViewById(R.id.promotion_name_edit);
        promotionDescriptionEditText = findViewById(R.id.promotion_description_edit);
        promotionPercentageEditText = findViewById(R.id.promotion_percentage_edit);
        savePromotionButton = findViewById(R.id.save_promotion_button);
        dbHandler = new DBHandler(AdminAddPromotionActivity.this);

        savePromotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String promotionName = promotionNameEditText.getText().toString();
                String promotionDescription = promotionDescriptionEditText.getText().toString();
                String promotionPercentageStr = promotionPercentageEditText.getText().toString();

                if (TextUtils.isEmpty(promotionName) || TextUtils.isEmpty(promotionDescription) || TextUtils.isEmpty(promotionPercentageStr)) {
                    Toast.makeText(AdminAddPromotionActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                float promotionPercentage;
                try {
                    promotionPercentage = Float.parseFloat(promotionPercentageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AdminAddPromotionActivity.this, "Invalid percentage value", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.addPromotion(promotionName, promotionDescription, promotionPercentage);
                Toast.makeText(AdminAddPromotionActivity.this, "Promotion added successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
