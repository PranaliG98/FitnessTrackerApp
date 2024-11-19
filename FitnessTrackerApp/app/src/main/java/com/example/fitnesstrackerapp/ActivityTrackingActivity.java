package com.example.fitnesstrackerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class ActivityTrackingActivity extends AppCompatActivity {
    private CardView cardRunning, cardJogging, cardWalking;

    private BarChart barChart;
    private Button leftButton, rightButton;
    private int currentWeekOffset = 0; // Track the current week offset

    private DatabaseHelper dbHelper;

    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        cardRunning = findViewById(R.id.RunningActivity);
        cardJogging = findViewById(R.id.JoggingActivity);
        cardWalking = findViewById(R.id.WalkingActivity);

        cardRunning.setOnClickListener(view -> {
            // Start activity for running
            startActivity(new Intent(ActivityTrackingActivity.this, RunningActivity.class));
        });

        cardJogging.setOnClickListener(view -> {
            // Start activity for jogging
            startActivity(new Intent(ActivityTrackingActivity.this, JoggingActivity.class));
        });

        cardWalking.setOnClickListener(view -> {
            // Start activity for walking
            startActivity(new Intent(ActivityTrackingActivity.this, WalkingActivity.class));
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getInt("USER_ID", 1); // Default to "User" if no name found

        barChart = findViewById(R.id.barChart);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        dbHelper = new DatabaseHelper(this);


        updateChart(0);


        // Button click listeners
        leftButton.setOnClickListener(v -> {

            updateChart(-1);
        });

        rightButton.setOnClickListener(v -> {

            updateChart(1);
        });
    }

    private void updateChart(int weekOffset) {
        currentWeekOffset += weekOffset;

        // Get start and end dates for the current week using Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Set to Monday
        calendar.add(Calendar.WEEK_OF_YEAR, currentWeekOffset); // Adjust by week offset

        Calendar dayCalendar = (Calendar) calendar.clone();

        // Get the start and end dates for the week
        String startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 6); // Move to the end of the week
        String endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        // Fetch data from the database
        Cursor cursor = dbHelper.getWeeklyCalories(userId, startDate, endDate);

        List<BarEntry> barEntries = new ArrayList<>();
        String[] dates = new String[7]; // Array for 7 days

        // Prepare data for the chart
        int index = 0;

        while (cursor.moveToNext()) {
            try {
                String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));
                float totalCalories = cursor.getFloat(cursor.getColumnIndexOrThrow("TOTAL_CALORIES"));

                // Parse date and get day of week (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date));
                int dayOfWeek = dateCalendar.get(Calendar.DAY_OF_WEEK)-2; // Convert to 0-6 (Monday-Sunday)
                barEntries.add(new BarEntry(dayOfWeek, totalCalories));
                dates[dayOfWeek] = date; // Store the date for x-axis labeling
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();


        // Fill missing dates with 0 calories, starting from the start of the week
        for (int i = 0; i < 7; i++) {
            // Set the calendar to the start of the week + i days
            //dayCalendar.add(Calendar.DAY_OF_WEEK, i); // Move to the i-th day of the week

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(dayCalendar.getTime());

            // Check if there's data for the current date
            boolean found = false;
            for (BarEntry entry : barEntries) {
                if (entry.getX() == i) { // Match the day index
                    found = true;
                    break;
                }
            }

            if (!found) {
                dates[i] = currentDate; // Store the date for x-axis labeling
                barEntries.add(new BarEntry(i, 0)); // No data for that day
            } else {
                // If data exists, ensure the correct date is stored
                dates[i] = currentDate; // Store the date for x-axis labeling
            }
            dayCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Create dataset and update chart
        BarDataSet barDataSet = new BarDataSet(barEntries, "Calories Burned");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        // Configure x-axis with date labels
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        barChart.invalidate(); // Refresh the chart
    }
}