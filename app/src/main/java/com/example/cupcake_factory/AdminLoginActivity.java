package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText adminNameEditText, adminPasswordEditText;
    private CheckBox showPasswordCheckBox;
    private Button loginButton;
    private TextView errorMessageTextView;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminNameEditText = findViewById(R.id.adminName);
        adminPasswordEditText = findViewById(R.id.adminPassword);
        showPasswordCheckBox = findViewById(R.id.showPassword);
        loginButton = findViewById(R.id.loginButton);
        errorMessageTextView = findViewById(R.id.errorMessage);

        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(view -> login());

        showPasswordCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                adminPasswordEditText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                adminPasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }
    private void login() {
        String adminName = adminNameEditText.getText().toString().trim();
        String adminPassword = adminPasswordEditText.getText().toString().trim();

        boolean isValid = databaseHelper.checkAdmin(adminName, adminPassword);

        if (isValid) {

            Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        } else {

            errorMessageTextView.setVisibility(android.view.View.VISIBLE);
            errorMessageTextView.setText("Invalid credentials. Please try again.");
        }
    }
}
