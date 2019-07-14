package com.example.csestudentmate.Home.Reminders.Features;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class ReminderManager {
    private AlarmManager alarmManager;
    private Calendar calendar;
    private Intent reminderIntent;
    private PendingIntent pendingReminderIntent;
    private Context context;

    private int REMINDER_REQUEST_CODE;
    private int hour;
    private int minute;
    private int second;
    private int day;
    private int month;
    private int year;

    public ReminderManager(Context context, Reminder reminder){
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        calendar = Calendar.getInstance();


        // Setting values
        this.hour = reminder.getHour();
        this.minute = reminder.getMinute();
        this.second = 0;
        this.day = reminder.getDay();
        this.month = reminder.getMonth();
        this.year = reminder.getYear();

        REMINDER_REQUEST_CODE = (int) reminder.getId();


        // Setting reminder Intent
        reminderIntent = new Intent(this.context, ReminderReceiver.class);

        // passing values to ReminderReceiver
        reminderIntent.putExtra("id", reminder.getId());
        reminderIntent.putExtra("title", reminder.getTitle());
        reminderIntent.putExtra("details", reminder.getDetails());
        reminderIntent.putExtra("hour", this.hour);
        reminderIntent.putExtra("minute", this.minute);
        reminderIntent.putExtra("day", this.day);
        reminderIntent.putExtra("month", this.month);
        reminderIntent.putExtra("year", this.year);

        // Setting time and dates into calendar
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minute);
        calendar.set(calendar.SECOND, second);
        calendar.set(calendar.DAY_OF_MONTH, day);
        calendar.set(calendar.MONTH, month);
        calendar.set(calendar.YEAR, year);

        // Setting reminder into Pending Intent (context, request code, intent, flags)
        pendingReminderIntent = PendingIntent.getBroadcast(context, REMINDER_REQUEST_CODE, reminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setReminder(){

        // Setting alarm manager to alarm on time
        if(calendar.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis())
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingReminderIntent);

    }

    public void cancelReminder(){
        // Setting alarm manager to cancel existing reminder
        alarmManager.cancel(pendingReminderIntent);
        Log.d("Cancel", "Reminder Received");
    }
}
