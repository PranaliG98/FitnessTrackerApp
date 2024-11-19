package com.example.fitnesstrackerapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String taskName = intent.getStringExtra("task_name");
        String activityType = intent.getStringExtra("activity_type");

        // Show the notification
        showNotification(context, taskName, activityType);
    }

    private void showNotification(Context context, String taskName, String activityType) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "reminder_channel";

        // Create a notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Reminder Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an intent to open your main activity or the activity of your choice when the notification is clicked
        Intent notificationIntent = new Intent(context, MainActivity.class); // Change this to your target activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Change this to your app's icon
                .setContentTitle("Reminder")
                .setContentText("Task: " + taskName + "\nActivity: " + activityType)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        notificationManager.notify((int) System.currentTimeMillis(), builder.build()); // Unique notification ID
    }
}
