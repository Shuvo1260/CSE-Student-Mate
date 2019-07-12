package com.example.csestudentmate.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.csestudentmate.Home.Util.Config;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    private static final int DatabaseVersion = Config.DATABASE_VERSION;

    private static final String DatabaseName = Config.DATABASE_NAME;


    private DatabaseHelper(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        // Creating Database
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creating notepad table
        String CREATE_NOTEPAD_TABLE = "CREATE TABLE " + Config.NOTEPAD_TABLE_NAME + "("
                + Config.COLUMN_NOTEPAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_NOTEPAD_TITLE + " TEXT, "
                + Config.COLUMN_NOTEPAD_NOTE + " TEXT " + ")";

        db.execSQL(CREATE_NOTEPAD_TABLE);

        // Creating reminder table
        String CREATE_REMINDER_TABLE = "CREATE TABLE " + Config.REMINDERS_TABLE_NAME + "("
                + Config.COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_REMINDER_TITLE + " TEXT, "
                + Config.COLUMN_REMINDER_DETAILS + " TEXT, "
                + Config.COLUMN_REMINDER_HOUR + " INTEGER, "
                + Config.COLUMN_REMINDER_MINUTE + " INTEGER, "
                + Config.COLUMN_REMINDER_DAY + " INTEGER, "
                + Config.COLUMN_REMINDER_MONTH + " INTEGER, "
                + Config.COLUMN_REMINDER_YEAR + " INTEGER, "
                + Config.COLUMN_REMINDER_ACTIVATED + " INTEGER " + ")";
        db.execSQL(CREATE_REMINDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrading database
        db.execSQL("DROP TABLE IF EXISTS " + Config.NOTEPAD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Config.REMINDERS_TABLE_NAME);

        onCreate(db);
    }
}
