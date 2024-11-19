package com.example.fitnesstrackerapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RunningActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepCountTextView, caloriesBurnedTextView;
    private ProgressBar circularProgressBar;
    private Button startButton, pauseButton, stopButton;

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int stepCount = 0;
    private boolean isCounting = false;

    // Average calories burned per step (approximate value)
    private static final double CALORIES_PER_STEP = 0.04; // adjust based on your own metrics
    private static final int STEP_GOAL = 10000; // Daily step goal for progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        // Initialize UI components
        stepCountTextView = findViewById(R.id.distanceTextView);
        caloriesBurnedTextView = findViewById(R.id.caloriesBurnedTextView);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        stopButton = findViewById(R.id.stopButton);

        // Initialize sensor manager and step sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


        // Initially, only the start button is visible
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.GONE);

        // Set button click listeners
        startButton.setOnClickListener(view -> startCounting());
        pauseButton.setOnClickListener(view -> pauseCounting());
        stopButton.setOnClickListener(view -> stopCounting());

        // Set initial progress bar values
        circularProgressBar.setMax(STEP_GOAL);
    }

    private void startCounting() {
        if (stepSensor != null) {

            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
        isCounting = true;
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    private void pauseCounting() {

        if (isCounting) {
            sensorManager.unregisterListener(this);
            // Resume activity logic here...
            pauseButton.setText("Resume");
            isCounting = false;
        }else{
            pauseButton.setText("Pause");
            isCounting = true;
        }

    }

    private void stopCounting() {
        isCounting = false;
        sensorManager.unregisterListener(this);
        stepCount = 0;
        updateUI();
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.GONE);
        pauseButton.setText("Pause");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (isCounting) {
            stepCount = (int) event.values[0];
            updateUI();
        }


    }

    private void updateUI() {
        stepCountTextView.setText(stepCount + " Steps");
        circularProgressBar.setProgress(stepCount);

        // Calculate calories burned
        double caloriesBurned = stepCount * CALORIES_PER_STEP;
        caloriesBurnedTextView.setText(String.format("%.2f Calories Burned", caloriesBurned));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isCounting) {
            sensorManager.unregisterListener(this);
            // Resume activity logic here...
            pauseButton.setText("Resume");
            isCounting = false;
        }else{
            pauseButton.setText("Pause");
            isCounting = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCounting) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }
}
