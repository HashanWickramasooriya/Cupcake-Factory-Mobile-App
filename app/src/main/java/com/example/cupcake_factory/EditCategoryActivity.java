package com.example.cupcake_factory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.content.Intent;

public class EditCategoryActivity extends AppCompatActivity {

    private EditText categoryNameEditText;
    private EditText categoryDescriptionEditText;
    private Button updateCategoryButton;
    private DatabaseHelper databaseHelper;
    private long categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        categoryNameEditText = findViewById(R.id.categoryName);
        categoryDescriptionEditText = findViewById(R.id.categoryDescription);
        updateCategoryButton = findViewById(R.id.updateCategoryButton);
        databaseHelper = new DatabaseHelper(this);

        categoryId = getIntent().getLongExtra("CATEGORY_ID", -1);

        loadCategoryDetails();

        updateCategoryButton.setOnClickListener(view -> updateCategory());
    }

    private void loadCategoryDetails() {
        Cursor cursor = databaseHelper.getCategoryById(categoryId);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME);
            int descIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_DESCRIPTION);

            if (nameIndex != -1 && descIndex != -1) {
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descIndex);
                categoryNameEditText.setText(name);
                categoryDescriptionEditText.setText(description);
            } else {
                Toast.makeText(this, "Error: Column index not found", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }



    private void updateCategory() {
        String name = categoryNameEditText.getText().toString().trim();
        String description = categoryDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = databaseHelper.updateCategory(categoryId, name, description);
        if (updated) {
            Toast.makeText(this, "Category updated", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("UPDATED_CATEGORY_NAME", name);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Failed to update category", Toast.LENGTH_SHORT).show();
        }
    }
}
