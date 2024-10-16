package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MemberHomeActivity extends AppCompatActivity {

    private Button viewCupcakesButton;
    private Button placeOrderButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        viewCupcakesButton = findViewById(R.id.viewCupcakesButton);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        logoutButton = findViewById(R.id.logoutButton);

        viewCupcakesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberHomeActivity.this, CupcakeListActivity.class);
                startActivity(intent);
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberHomeActivity.this,PlaceOrderActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberHomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
