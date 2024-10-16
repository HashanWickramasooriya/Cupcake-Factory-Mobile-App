package com.example.cupcake_factory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateCategoriesActivity extends AppCompatActivity {

    private EditText categoryNameEditText;
    private EditText categoryDescriptionEditText;
    private Button submitCategoryButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_categories);

        categoryNameEditText = findViewById(R.id.categoryName);
        categoryDescriptionEditText = findViewById(R.id.categoryDescription);
        submitCategoryButton = findViewById(R.id.submitCategoryButton);

        databaseHelper = new DatabaseHelper(this);

        submitCategoryButton.setOnClickListener(view -> createCategory());
    }

    private void createCategory() {
        String categoryName = categoryNameEditText.getText().toString().trim();
        String categoryDescription = categoryDescriptionEditText.getText().toString().trim();

        if (categoryName.isEmpty() || categoryDescription.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = databaseHelper.addCategory(categoryName, categoryDescription);

        if (result != -1) {
            Toast.makeText(this, "Category created successfully", Toast.LENGTH_SHORT).show();
            categoryNameEditText.setText("");
            categoryDescriptionEditText.setText("");
        } else {
            Toast.makeText(this, "Failed to create category", Toast.LENGTH_SHORT).show();
        }
    }
}
