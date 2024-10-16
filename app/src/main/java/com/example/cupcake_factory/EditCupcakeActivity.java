package com.example.cupcake_factory;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditCupcakeActivity extends AppCompatActivity {

    private EditText cupcakeNameEditText;
    private EditText cupcakeDescriptionEditText;
    private EditText cupcakePriceEditText;
    private ImageView cupcakeImageView;
    private Button updateCupcakeButton;
    private DatabaseHelper databaseHelper;
    private long cupcakeId;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cupcake);

        cupcakeNameEditText = findViewById(R.id.cupcakeName);
        cupcakeDescriptionEditText = findViewById(R.id.cupcakeDescription);
        cupcakePriceEditText = findViewById(R.id.cupcakePrice);
        cupcakeImageView = findViewById(R.id.cupcakeImageView);
        updateCupcakeButton = findViewById(R.id.updateCupcakeButton);
        databaseHelper = new DatabaseHelper(this);

        cupcakeId = getIntent().getLongExtra("CUPCAKE_ID", -1);

        loadCupcakeDetails();

        cupcakeImageView.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });

        updateCupcakeButton.setOnClickListener(view -> updateCupcake());
    }

    private void loadCupcakeDetails() {
        Cursor cursor = databaseHelper.getCupcakeById(cupcakeId);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUPCAKE_NAME);
            int descIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUPCAKE_DESCRIPTION);
            int imageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUPCAKE_IMAGE);
            int priceIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CUPCAKE_PRICE);

            if (nameIndex != -1 && descIndex != -1 && imageIndex != -1 && priceIndex != -1) {
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descIndex);
                imagePath = cursor.getString(imageIndex);
                double price = cursor.getDouble(priceIndex);

                cupcakeNameEditText.setText(name);
                cupcakeDescriptionEditText.setText(description);
                cupcakePriceEditText.setText(String.valueOf(price));

                if (imagePath != null && !imagePath.isEmpty()) {
                    cupcakeImageView.setImageURI(Uri.parse(imagePath));
                }
            } else {
                Toast.makeText(this, "Database column indices not found", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Cupcake not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void updateCupcake() {
        String name = cupcakeNameEditText.getText().toString().trim();
        String description = cupcakeDescriptionEditText.getText().toString().trim();
        String priceString = cupcakePriceEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || imagePath == null || imagePath.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);

        boolean updated = databaseHelper.updateCupcake(cupcakeId, name, description, imagePath, price);
        if (updated) {
            Toast.makeText(this, "Cupcake updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update cupcake", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imagePath = getPathFromUri(selectedImage);
            cupcakeImageView.setImageURI(selectedImage);
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }
}
