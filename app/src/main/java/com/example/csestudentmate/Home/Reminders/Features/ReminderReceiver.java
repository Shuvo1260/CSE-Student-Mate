package com.example.csestudentmate.Home.Reminders.Features;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.csestudentmate.Home.Reminders.Database.ReminderDatabaseQuery;
import com.example.csestudentmate.R;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Reminder Receiver", "Reminder Received: ");

        try {
            // For updated version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel("reminderNotifications", "reminderNotifications",
                        NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "reminderNotifications");

            // Receiving values
            Bundle bundle = intent.getExtras();

            Reminder reminder = new Reminder(bundle.getLong("id"), bundle.getString("title"), bundle.getString("details"),
                    bundle.getInt("hour"), bundle.getInt("minute"), bundle.getInt("day"), bundle.getInt("month"),
                    bundle.getInt("year"), 0);

            // Setting notification
            notification.setContentTitle(reminder.getTitle());
            notification.setContentText(reminder.getDetails());
            notification.setAutoCancel(true);
            notification.setSmallIcon(R.drawable.logo_white);
            notification.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            notification.setDefaults(NotificationCompat.DEFAULT_ALL);

            // Creating notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify((int) reminder.getId(), notification.build());

            // Updating Database to set the reminder is done
            ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(context);
            reminderDatabaseQuery.update(reminder);
        }catch (Exception e){
            Log.d("Notification", "Reminder Received: " + e.getMessage());
        }
    }
}
