package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_start);

        Button adminRegisterButton = findViewById(R.id.admin_register_button);
        adminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminStartActivity.this, AdminRegistrationActivity.class);
                startActivity(intent);
            }
        });

        Button adminSignInButton = findViewById(R.id.admin_sign_in_button);
        adminSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminStartActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
