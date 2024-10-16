package com.example.cupcake_factory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextMessage;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String message = editTextMessage.getText().toString();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(ContactUsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(ContactUsActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();


                editTextName.setText("");
                editTextEmail.setText("");
                editTextMessage.setText("");
            }
        });
    }
}
