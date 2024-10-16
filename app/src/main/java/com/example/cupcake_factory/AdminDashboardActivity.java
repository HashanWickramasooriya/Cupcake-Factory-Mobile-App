package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button createCategoriesButton;
    private Button createCupcakesButton;
    private Button manageCategoriesButton;
    private Button manageCupcakesButton;
    private Button viewOrdersButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        createCategoriesButton = findViewById(R.id.createCategoriesButton);
        createCupcakesButton = findViewById(R.id.createCupcakesButton);
        manageCategoriesButton = findViewById(R.id.manageCategoriesButton);
        manageCupcakesButton = findViewById(R.id.manageCupcakesButton);
        viewOrdersButton = findViewById(R.id.viewOrdersButton);
        logoutButton = findViewById(R.id.logoutButton);

        createCategoriesButton.setOnClickListener(view -> openCreateCategories());
        createCupcakesButton.setOnClickListener(view -> openCreateCupcakes());
        manageCategoriesButton.setOnClickListener(view -> openManageCategories());
        manageCupcakesButton.setOnClickListener(view -> openManageCupcakes());
        viewOrdersButton.setOnClickListener(view -> openViewOrders()); // Corrected click listener
        logoutButton.setOnClickListener(view -> logoutAdmin());
    }

    private void openCreateCategories() {
        Intent intent = new Intent(AdminDashboardActivity.this, CreateCategoriesActivity.class);
        startActivity(intent);
    }

    private void openCreateCupcakes() {
        Intent intent = new Intent(AdminDashboardActivity.this, CreateCupcakesActivity.class);
        startActivity(intent);
    }

    private void openManageCategories() {
        Intent intent = new Intent(AdminDashboardActivity.this, ManageCategoriesActivity.class);
        startActivity(intent);
    }

    private void openManageCupcakes() {

        Intent intent = new Intent(AdminDashboardActivity.this, ManageCupcakesActivity.class);
        startActivity(intent);
    }

    private void openViewOrders() {

        Intent intent = new Intent(AdminDashboardActivity.this, ViewOrdersActivity.class);
        startActivity(intent);
    }

    private void logoutAdmin() {
       
        Intent intent = new Intent(AdminDashboardActivity.this, AdminLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
