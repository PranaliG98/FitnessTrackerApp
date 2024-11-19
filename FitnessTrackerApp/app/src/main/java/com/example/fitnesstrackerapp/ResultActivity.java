package com.example.fitnesstrackerapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

    public class ResultActivity extends AppCompatActivity {

        private TextView conditionTextView, bmiTextView, ageTextView, suggestionTextView;
        private ImageView shareButton, recalculateButton;
        private CardView resultCard, suggestionCard;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);

            // Initialize UI components
            conditionTextView = findViewById(R.id.condition);
            bmiTextView = findViewById(R.id.your_bmi);
            ageTextView = findViewById(R.id.ageTxt);
            suggestionTextView = findViewById(R.id.suggestion);
            shareButton = findViewById(R.id.share);
            recalculateButton = findViewById(R.id.recalculate);
            resultCard = findViewById(R.id.result_card);
            suggestionCard = findViewById(R.id.sug_container);

            // Retrieve data from Intent
            Intent intent = getIntent();
            String condition = intent.getStringExtra("condition");
            String bmi = intent.getStringExtra("bmi");
            String age = intent.getStringExtra("age");
            String suggestion = intent.getStringExtra("suggestion");

            // Set retrieved data to the views
            conditionTextView.setText(condition);
            bmiTextView.setText(bmi);
            ageTextView.setText(age);
            suggestionTextView.setText(suggestion);

            // Share button functionality
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shareText = "My BMI is " + bmi + ". " + condition + ". Age: " + age + ". " + suggestion;
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
            });

            // Recalculate button functionality
            recalculateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Go back to the main activity or calculation screen
                    Intent recalculateIntent = new Intent(ResultActivity.this, MainActivity.class);
                    startActivity(recalculateIntent);
                    finish(); // Finish the current activity
                }
            });
        }
    }
