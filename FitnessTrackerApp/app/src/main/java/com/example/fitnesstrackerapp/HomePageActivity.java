package com.example.fitnesstrackerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.fitnesstrackerapp.fragments.NotificationFragment;
import com.example.fitnesstrackerapp.fragments.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    // Declare variables for each CardView
    private CardView clothingCardLayout, activityTrackingLayout, waterIntakeLayout, caloriesTrackingLayout;
    private FrameLayout fragmentContainer;
    private View homeDashboard;  // Change from FrameLayout to View
    TextView userNameTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Initialize views
        homeDashboard = findViewById(R.id.DashboardLayout);  // Ensure this ID matches your layout
        fragmentContainer = findViewById(R.id.fragmentContainer); // This should be the ID for your fragment container

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Initialize the CardViews
        clothingCardLayout = findViewById(R.id.Dashboard);  // Card for Calculate BMI
        activityTrackingLayout = findViewById(R.id.ActivityTrackingLayout);  // Card for Activity Tracking
        waterIntakeLayout = findViewById(R.id.WaterIntakeLayout);  // Card for Water Intake
        caloriesTrackingLayout = findViewById(R.id.CaloriesTrackingLayout);  // Card for Calories Tracking

        // Set click listeners for each CardView
        setCardViewClickListeners();

        // Find the TextView for the user's name
        userNameTextView = findViewById(R.id.UName);
        // Get the logged-in user's name from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("USER_NAME", "User"); // Default to "User" if no name found

        // Set the TextView to say "Hi, [UserName]"
        userNameTextView.setText("Hi, " + userName + "!");

        // Set up bottom navigation item selections
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Handle navigation based on selected item
            if (item.getItemId() == R.id.nav_home) {
                // Show the dashboard and hide the fragments
                homeDashboard.setVisibility(View.VISIBLE);
                fragmentContainer.setVisibility(View.GONE);
                return true;
            } else if (item.getItemId() == R.id.nav_notification) {
                selectedFragment = new NotificationFragment();
            } else if (item.getItemId() == R.id.nav_user) {
                selectedFragment = new UserFragment();
            }

            // If a fragment is selected, hide the dashboard and show the selected fragment
            if (selectedFragment != null) {
                homeDashboard.setVisibility(View.GONE);
                fragmentContainer.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    // Method to set click listeners for each CardView
    private void setCardViewClickListeners() {
        clothingCardLayout.setOnClickListener(v -> {
            // Navigate to Calculate BMI page
            Intent intent = new Intent(HomePageActivity.this, BMICalculatorActivity.class);
            startActivity(intent);
        });

        activityTrackingLayout.setOnClickListener(v -> {
            // Navigate to Activity Tracking page
            Intent intent = new Intent(HomePageActivity.this, ActivityTrackingActivity.class);
            startActivity(intent);
        });

        waterIntakeLayout.setOnClickListener(v -> {
            // Navigate to Water Intake tracking page
            Intent intent = new Intent(HomePageActivity.this, WaterIntakeActivity.class);
            startActivity(intent);
        });

        caloriesTrackingLayout.setOnClickListener(v -> {
            // Navigate to Calories Tracking page
            Intent intent = new Intent(HomePageActivity.this, CaloriesTrackingActivity.class);
            startActivity(intent);
        });
    }
}
