package com.example.fitnesstrackerapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    private EditText taskNameEditText;
    private TextView dateTextView, timeTextView;
    private Spinner activityTypeSpinner;
    private Button saveReminderButton;

    private int selectedYear, selectedMonth, selectedDay;
    private int selectedHour, selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        taskNameEditText = findViewById(R.id.taskNameEditText);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        activityTypeSpinner = findViewById(R.id.activityTypeSpinner);
        saveReminderButton = findViewById(R.id.saveReminderButton);

        // Set up the spinner for activity types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityTypeSpinner.setAdapter(adapter);

        dateTextView.setOnClickListener(v -> showDatePicker());
        timeTextView.setOnClickListener(v -> showTimePicker());

        saveReminderButton.setOnClickListener(v -> saveReminder());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = dayOfMonth;
                    dateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        selectedHour = calendar.get(Calendar.HOUR_OF_DAY);
        selectedMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    selectedHour = hourOfDay;
                    selectedMinute = minute;
                    timeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
                }, selectedHour, selectedMinute, true);
        timePickerDialog.show();
    }

    private void saveReminder() {
        String taskName = taskNameEditText.getText().toString();
        String activityType = activityTypeSpinner.getSelectedItem().toString();

        // Set the reminder time
        Calendar calendar = Calendar.getInstance();
        calendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute, 0);

        // Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("task_name", taskName);
        intent.putExtra("activity_type", activityType);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Return to previous activity or show a message
        finish();
    }
}
