package com.example.csestudentmate.Home.NotepadPage.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.csestudentmate.Home.Util.Config;

public class NotepadDatabaseHelper extends SQLiteOpenHelper {

    private static NotepadDatabaseHelper notepadDatabaseHelper;

    private static final int DatabaseVersion = 1;

    private static final String DatabaseName = Config.DATABASE_NAME;


    private NotepadDatabaseHelper(Context context){
        super(context, DatabaseName, null, DatabaseVersion);
    }

    public static synchronized NotepadDatabaseHelper getInstance(Context context){
        if(notepadDatabaseHelper == null){
            notepadDatabaseHelper = new NotepadDatabaseHelper(context);
        }
        return notepadDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTEPAD_TABLE = "CREATE TABLE " + Config.NOTEPAD_TABLE_NAME + "("
                + Config.COLUMN_NOTEPAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_NOTEPAD_TITLE + " TEXT, "
                + Config.COLUMN_NOTEPAD_NOTE + " TEXT " + ")";

        db.execSQL(CREATE_NOTEPAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.NOTEPAD_TABLE_NAME);

        onCreate(db);
    }
}
