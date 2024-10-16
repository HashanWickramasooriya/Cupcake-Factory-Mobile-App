package com.example.cupcake_factory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageCategoriesActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_categories);

        listView = findViewById(R.id.categoriesListView);
        categoryList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        loadCategories();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showCategoryOptions(position);
            }
        });
    }

    private void loadCategories() {
        Cursor cursor = databaseHelper.getAllCategories();
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
                categoryList.add(categoryName);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            if (cursor != null) {
                cursor.close();
            }
            Toast.makeText(this, "No categories found", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryList);
        listView.setAdapter(adapter);
    }

    private void showCategoryOptions(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Category Options");

        String[] options = {"Edit Category", "Delete Category"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:
                        editCategory(position);
                        break;
                    case 1:
                        deleteCategory(position);
                        break;
                }
            }
        });

        builder.create().show();
    }

    private void editCategory(int position) {
        Cursor cursor = databaseHelper.getAllCategories();

        if (cursor != null && cursor.moveToPosition(position)) {
            long categoryId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
            cursor.close();

            Intent intent = new Intent(ManageCategoriesActivity.this, EditCategoryActivity.class);
            intent.putExtra("CATEGORY_ID", categoryId);
            startActivity(intent);
        } else {
            if (cursor != null) {
                cursor.close();
            }
            Toast.makeText(this, "Error retrieving category information", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCategory(int position) {
        Cursor cursor = databaseHelper.getAllCategories();

        if (cursor != null && cursor.moveToPosition(position)) {
            long categoryId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_ID));
            String categoryName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME));
            cursor.close();

            boolean deleted = databaseHelper.deleteCategory(categoryId);
            if (deleted) {
                categoryList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(ManageCategoriesActivity.this, "Category '" + categoryName + "' deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ManageCategoriesActivity.this, "Failed to delete category", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (cursor != null) {
                cursor.close();
            }
            Toast.makeText(this, "Error retrieving category information", Toast.LENGTH_SHORT).show();
        }
    }
}

