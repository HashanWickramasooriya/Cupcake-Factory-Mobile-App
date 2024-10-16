package com.example.cupcake_factory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MemberLoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signupLink;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupLink = findViewById(R.id.signupLink);
        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (db.checkMember(email, password)) {
                    Intent intent = new Intent(MemberLoginActivity.this, MemberHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MemberLoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberLoginActivity.this, MemberSignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
