package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    private EditText customerNameEditText;
    private ListView cupcakesListView;
    private Button placeOrderButton;
    private DatabaseHelper databaseHelper;
    private List<Cupcake> selectedCupcakes;
    private double totalAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_orders);

        customerNameEditText = findViewById(R.id.customerName);
        cupcakesListView = findViewById(R.id.cupcakesListView);
        placeOrderButton = findViewById(R.id.placeOrderButton);
        databaseHelper = new DatabaseHelper(this);

        loadCupcakes();

        placeOrderButton.setOnClickListener(view -> {
            String customerName = customerNameEditText.getText().toString().trim();
            StringBuilder orderDetails = new StringBuilder();
            totalAmount = 0.0;

            if (customerName.isEmpty() || selectedCupcakes == null || selectedCupcakes.isEmpty()) {
                Toast.makeText(PlaceOrderActivity.this, "Please enter your name and select at least one cupcake", Toast.LENGTH_SHORT).show();
                return;
            }

            for (Cupcake cupcake : selectedCupcakes) {
                orderDetails.append(cupcake.getName()).append(" - $").append(cupcake.getPrice()).append("\n");
                totalAmount += cupcake.getPrice();
            }

            long result = databaseHelper.insertOrder(customerName, orderDetails.toString(), totalAmount);
            if (result != -1) {
                Toast.makeText(PlaceOrderActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PlaceOrderActivity.this, MemberHomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(PlaceOrderActivity.this, "Order placement failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCupcakes() {
        selectedCupcakes = databaseHelper.getAllCupcakes();

        if (selectedCupcakes == null || selectedCupcakes.isEmpty()) {
            Toast.makeText(this, "No cupcakes available", Toast.LENGTH_SHORT).show();
        } else {
            CupcakeAdapter adapter = new CupcakeAdapter(this, selectedCupcakes);
            cupcakesListView.setAdapter(adapter);
        }
    }
}
