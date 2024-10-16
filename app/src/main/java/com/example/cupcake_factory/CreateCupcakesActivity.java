package com.example.cupcake_factory;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CreateCupcakesActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText cupcakeNameEditText, cupcakeDescriptionEditText, cupcakePriceEditText;
    private Button selectImageButton, submitCupcakeButton;
    private ImageView selectedImageView;
    private Uri selectedImageUri;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cupcakes);

        cupcakeNameEditText = findViewById(R.id.cupcakeName);
        cupcakeDescriptionEditText = findViewById(R.id.cupcakeDescription);
        cupcakePriceEditText = findViewById(R.id.cupcakePrice);
        selectImageButton = findViewById(R.id.selectImageButton);
        submitCupcakeButton = findViewById(R.id.submitCupcakeButton);
        selectedImageView = findViewById(R.id.selectedImageView);
        selectedImageView.setVisibility(View.GONE);

        databaseHelper = new DatabaseHelper(this);

        selectImageButton.setOnClickListener(view -> openImageSelector());

        submitCupcakeButton.setOnClickListener(view -> createCupcake());
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                selectedImageView.setImageBitmap(bitmap);
                selectedImageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createCupcake() {
        String name = cupcakeNameEditText.getText().toString().trim();
        String description = cupcakeDescriptionEditText.getText().toString().trim();
        String priceString = cupcakePriceEditText.getText().toString().trim();
        double price = Double.parseDouble(priceString);

        if (name.isEmpty() || description.isEmpty() || priceString.isEmpty() || selectedImageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }

        String imagePath = getPathFromUri(selectedImageUri);

        long result = databaseHelper.addCupcake(name, description, imagePath, price);

        if (result > 0) {
            Toast.makeText(this, "Cupcake created successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to create cupcake. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }
}
