package com.example.fitnesstrackerapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.google.android.material.button.MaterialButton;

public class BMICalculatorActivity extends AppCompatActivity {

    private CardView maleCard, femaleCard;
    private SeekBar heightSeekBar;
    private TextView heightTxt, weightTxt, ageTxt;
    private RelativeLayout weightMinus, weightPlus, ageMinus, agePlus;
    private MaterialButton calculateBtn;

    private float currentWeight = 50;
    private int currentAge = 19;
    private int currentHeight = 150;  // Initial value for height in cm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        // Initialize views
        maleCard = findViewById(R.id.cardView_male);
        femaleCard = findViewById(R.id.cardView_female);

        heightSeekBar = findViewById(R.id.Seekbar);
        heightTxt = findViewById(R.id.height_txt);
        weightTxt = findViewById(R.id.weight_txt);
        ageTxt = findViewById(R.id.age);
        weightMinus = findViewById(R.id.weight_minus);
        weightPlus = findViewById(R.id.weight_plus);
        ageMinus = findViewById(R.id.age_minus);
        agePlus = findViewById(R.id.age_plus);
        calculateBtn = findViewById(R.id.calculate);

        // Gender selection click listeners
        maleCard.setOnClickListener(v -> {
            // Change the background color when male card is selected
            maleCard.setCardBackgroundColor(getResources().getColor(R.color.selected_card_background));
            femaleCard.setCardBackgroundColor(getResources().getColor(R.color.card_background));
        });

        femaleCard.setOnClickListener(v -> {
            // Change the background color when female card is selected
            femaleCard.setCardBackgroundColor(getResources().getColor(R.color.selected_card_background));
            maleCard.setCardBackgroundColor(getResources().getColor(R.color.card_background));
        });

        // Set initial values
        weightTxt.setText(String.valueOf(currentWeight));
        ageTxt.setText(String.valueOf(currentAge));
        heightTxt.setText(String.valueOf(currentHeight));

        // SeekBar Listener for Height
        heightSeekBar.setMax(200);  // Max height in cm
        heightSeekBar.setProgress(currentHeight);  // Default height
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentHeight = progress;
                heightTxt.setText(String.valueOf(currentHeight));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // Event listeners for Weight controls
        weightMinus.setOnClickListener(v -> {
            if (currentWeight > 1) {
                currentWeight--;
                weightTxt.setText(String.valueOf(currentWeight));
            }
        });

        weightPlus.setOnClickListener(v -> {
            currentWeight++;
            weightTxt.setText(String.valueOf(currentWeight));
        });

        // Event listeners for Age controls
        ageMinus.setOnClickListener(v -> {
            if (currentAge > 1) {
                currentAge--;
                ageTxt.setText(String.valueOf(currentAge));
            }
        });

        agePlus.setOnClickListener(v -> {
            currentAge++;
            ageTxt.setText(String.valueOf(currentAge));
        });

        // Calculate button logic
        calculateBtn.setOnClickListener(v -> {
            int height = Integer.parseInt(heightTxt.getText().toString());
            float weight = Float.parseFloat(weightTxt.getText().toString());
            int age = Integer.parseInt(ageTxt.getText().toString());

            if (height > 0 && weight > 0) {
                double heightInMeters = height / 100.0;
                double bmi = weight / (heightInMeters * heightInMeters);

                // Use BMIUtils to get the category and suggestions
                String condition = BMIUtils.checkAdult(currentAge, (float) bmi);
                String suggestion = BMIUtils.getSuggestions((float) bmi);

                // Start ResultActivity and pass data
                Intent resultIntent = new Intent(BMICalculatorActivity.this, ResultActivity.class);
                resultIntent.putExtra("condition", condition);
                resultIntent.putExtra("bmi", String.format("%.2f", bmi));
                resultIntent.putExtra("age", String.valueOf(currentAge));
                resultIntent.putExtra("suggestion", suggestion);
                startActivity(resultIntent);
            }else {
                Toast.makeText(BMICalculatorActivity.this, "Please enter valid values", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
