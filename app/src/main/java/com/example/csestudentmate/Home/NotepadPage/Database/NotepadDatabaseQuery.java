package com.example.csestudentmate.Home.NotepadPage.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.csestudentmate.Home.NotepadPage.Features.Note;
import com.example.csestudentmate.Home.Util.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotepadDatabaseQuery {

    private Context context;

    public NotepadDatabaseQuery(Context context){
        this.context = context;
    }

    public long insert(Note note){
        long noteId = -1;

        NotepadDatabaseHelper notepadDatabaseHelper = NotepadDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = notepadDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.COLUMN_NOTEPAD_TITLE, note.getTitle());
        contentValues.put(Config.COLUMN_NOTEPAD_NOTE, note.getNote());

        try{
            noteId = sqLiteDatabase.insertOrThrow(Config.NOTEPAD_TABLE_NAME, null, contentValues);
        }catch (Exception e){
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }
        return noteId;
    }

    public long delete(Note note){
        long noteId = -1;
        NotepadDatabaseHelper notepadDatabaseHelper = NotepadDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = notepadDatabaseHelper.getWritableDatabase();
        try{
            noteId = sqLiteDatabase.delete(Config.NOTEPAD_TABLE_NAME, Config.COLUMN_NOTEPAD_ID + " = ? ", new String[] {String.valueOf(note.getId())});
        }catch (Exception e){
            Toast.makeText(context, "Operation Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }
        return noteId;
    }

    public long update(Note note){
        long noteId = -1;

        NotepadDatabaseHelper notepadDatabaseHelper = NotepadDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = notepadDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Config.COLUMN_NOTEPAD_TITLE, note.getTitle());
        contentValues.put(Config.COLUMN_NOTEPAD_NOTE, note.getNote());

        try{
            noteId = sqLiteDatabase.update(Config.NOTEPAD_TABLE_NAME, contentValues,
                    Config.COLUMN_NOTEPAD_ID + " = ? ",
                    new String[] {String.valueOf(note.getId())});
        }catch (Exception e){
            Toast.makeText(context, "Update Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            sqLiteDatabase.close();
        }

        return noteId;
    }

    public List<Note> getAllNotes(){
        NotepadDatabaseHelper notepadDatabaseHelper = NotepadDatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = notepadDatabaseHelper.getReadableDatabase();

        List<Note> noteList = new ArrayList<>();

        Cursor cursor = null;

        try{
            cursor = sqLiteDatabase.query(Config.NOTEPAD_TABLE_NAME,null, null, null, null, null, null, null);
            if(cursor != null &&cursor.moveToLast()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_NOTEPAD_ID));
                    String title = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTEPAD_TITLE));
                    String note = cursor.getString(cursor.getColumnIndex(Config.COLUMN_NOTEPAD_NOTE));

                    noteList.add(new Note(id, title, note));
                } while (cursor.moveToPrevious());
                return noteList;
            }

        }catch (Exception e){
            Toast.makeText(context, "Note list loading Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            if (cursor != null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }


}
