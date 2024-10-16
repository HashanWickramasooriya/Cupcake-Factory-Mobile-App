package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Button nextButton = findViewById(R.id.nextButton);


        nextButton.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
