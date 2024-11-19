package com.example.fitnesstrackerapp;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password, confirmPassword;
    Button signUpButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextname);
        email = findViewById(R.id.editTextemail);
        password = findViewById(R.id.editTextpassword);
        confirmPassword = findViewById(R.id.editTextconfirmpassword);
        signUpButton = findViewById(R.id.signupbutton);

        db = new DatabaseHelper(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();

                if (userPassword.equals(userConfirmPassword)) {
                    long result = db.addUser(userName, userEmail, userPassword);
                    if (result != -1) {
                        Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
