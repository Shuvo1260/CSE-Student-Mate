package com.example.csestudentmate.Home.Reminders.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.csestudentmate.Home.Util.Config;

public class ReminderDatabaseHelper extends SQLiteOpenHelper {

    private static ReminderDatabaseHelper reminderDatabaseHelper;

    private static final int DatabaseVersion = 1;

    private static final String DatabaseName = Config.DATABASE_NAME;

    private ReminderDatabaseHelper(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }

    private static synchronized ReminderDatabaseHelper getInstance(Context context){
        if(reminderDatabaseHelper == null){
            reminderDatabaseHelper = new ReminderDatabaseHelper(context);
        }

        return reminderDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.REMINDERS_TABLE_NAME);
        onCreate(db);
    }
}
