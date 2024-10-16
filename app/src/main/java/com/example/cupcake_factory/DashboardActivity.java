package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button adminButton = findViewById(R.id.adminButton);
        Button memberButton = findViewById(R.id.memberButton);
        Button contactUsButton = findViewById(R.id.contactUsButton);

        adminButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });

        memberButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, MemberLoginActivity.class);
            startActivity(intent);
        });

        contactUsButton.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });
    }
}
