package com.example.fitnesstrackerapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton;
    TextView signUpText;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextemail);
        password = findViewById(R.id.editTextpassword);
        loginButton = findViewById(R.id.loginbutton);
        signUpText = findViewById(R.id.textview32);

        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                Cursor res = db.getUser(userEmail, userPassword);
                if (res.getCount() > 0) {
                    try {
                        res.moveToFirst();
                        int userId = res.getInt(res.getColumnIndexOrThrow("ID"));
                        String userName = res.getString(res.getColumnIndexOrThrow("NAME"));
                        // Store user information in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("USER_ID", userId); // Store user ID
                        editor.putString("USER_NAME", userName);
                        editor.putString("USER_EMAIL", userEmail); // Store user email
                        editor.apply(); // Save changes


                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(intent);

                    }catch (Exception e) {
                        // This will catch the case where the column doesn't exist
                        Toast.makeText(LoginActivity.this, "Error retrieving user ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
