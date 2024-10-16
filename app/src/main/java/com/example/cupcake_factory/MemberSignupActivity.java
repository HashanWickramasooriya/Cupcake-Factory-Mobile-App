package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MemberSignupActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_signup);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        db = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                long result = db.insertMember(username, email, password);
                if (result > 0) {
                    Toast.makeText(MemberSignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MemberSignupActivity.this, MemberLoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MemberSignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
