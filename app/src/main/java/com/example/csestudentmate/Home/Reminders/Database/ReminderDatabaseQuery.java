package com.example.csestudentmate.Home.Reminders.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.csestudentmate.DatabaseHelper.DatabaseHelper;
import com.example.csestudentmate.Home.Reminders.Features.Reminder;
import com.example.csestudentmate.Home.Util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReminderDatabaseQuery {

    private Context context;

    public ReminderDatabaseQuery(Context context){
        this.context = context;
    }

    public long insert(Reminder reminder){
        long reminderId = -1;

        DatabaseHelper reminderDatabaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = reminderDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.COLUMN_REMINDER_TITLE, reminder.getTitle());
        contentValues.put(Config.COLUMN_REMINDER_DETAILS, reminder.getDetails());
        contentValues.put(Config.COLUMN_REMINDER_HOUR, reminder.getHour());
        contentValues.put(Config.COLUMN_REMINDER_MINUTE, reminder.getMinute());
        contentValues.put(Config.COLUMN_REMINDER_DAY, reminder.getDay());
        contentValues.put(Config.COLUMN_REMINDER_MONTH, reminder.getMonth());
        contentValues.put(Config.COLUMN_REMINDER_YEAR, reminder.getYear());

        try {
            reminderId = sqLiteDatabase.insertOrThrow(Config.REMINDERS_TABLE_NAME, null, contentValues);
        }catch (Exception e){
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }
        return reminderId;
    }

    public long delete(Reminder reminder){
        long reminderId = -1;

        DatabaseHelper reminderDatabaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = reminderDatabaseHelper.getWritableDatabase();


        try {
            reminderId = sqLiteDatabase.delete(Config.REMINDERS_TABLE_NAME,
                    Config.COLUMN_REMINDER_ID + " = ? ",
                    new String[] {String.valueOf(reminder.getId())});
        }catch (Exception e){
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }
        return reminderId;
    }

    public long update(Reminder reminder){
        long reminderId = -1;
        DatabaseHelper reminderDatabaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = reminderDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_REMINDER_ID, reminder.getId());
        contentValues.put(Config.COLUMN_REMINDER_TITLE, reminder.getTitle());
        contentValues.put(Config.COLUMN_REMINDER_DETAILS, reminder.getDetails());
        contentValues.put(Config.COLUMN_REMINDER_HOUR, reminder.getHour());
        contentValues.put(Config.COLUMN_REMINDER_MINUTE, reminder.getMinute());
        contentValues.put(Config.COLUMN_REMINDER_DAY, reminder.getDay());
        contentValues.put(Config.COLUMN_REMINDER_MONTH, reminder.getMonth());
        contentValues.put(Config.COLUMN_REMINDER_YEAR, reminder.getYear());

        try {
            reminderId = sqLiteDatabase.update(Config.REMINDERS_TABLE_NAME, contentValues,
                    Config.COLUMN_REMINDER_ID + " = ?",
                    new String[] {String.valueOf(reminder.getId())});
        }catch (Exception e){
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }
        return reminderId;
    }

    public List<Reminder> getAllReminders(){
        List<Reminder> reminderList = new ArrayList<>();

        DatabaseHelper reminderDatabaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = reminderDatabaseHelper.getReadableDatabase();

        Cursor cursor = null;


        try {
            cursor = sqLiteDatabase.query(Config.REMINDERS_TABLE_NAME, null, null, null, null, null, null, null);

            if(cursor != null &&cursor.moveToLast()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_REMINDER_ID));
                    String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_REMINDER_TITLE));
                    String details = cursor.getString(cursor.getColumnIndex(Config.COLUMN_REMINDER_DETAILS));
                    int hour = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_REMINDER_HOUR));
                    int minute = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_REMINDER_MINUTE));
                    int day = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_REMINDER_DAY));
                    int month = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_REMINDER_MONTH));
                    int year = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_REMINDER_YEAR));

                    reminderList.add(new Reminder(id, title, details, hour, minute, day, month, year));
                } while (cursor.moveToPrevious());
                return reminderList;
            }
        }catch (Exception e){
            Toast.makeText(context, "Reminder list loading failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            if(cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }
}
