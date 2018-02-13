package com.example.android.pfpnotes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ahmed on 13/02/2018.
 */

public class NotesDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "NotesDbHelper";
    public static final String DATABASE_NAME = "pfpnotes.db";
    public static final int DATABASE_VERSION = 2;

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " +
                NotesContract.NoteEntry.TABLE_NAME + " (" +
                NotesContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesContract.NoteEntry.COLUMN_PLACE + " TEXT NOT NULL," +
                NotesContract.NoteEntry.COLUMN_SHAPE + " INTEGER NOT NULL DEFAULT 0," +
                NotesContract.NoteEntry.COLUMN_WIDTH + " REAL NOT NULL DEFAULT 0," +
                NotesContract.NoteEntry.COLUMN_HEIGHT + " REAL NOT NULL DEFAULT 0," +
                NotesContract.NoteEntry.COLUMN_SKIN + " INTEGER NOT NULL DEFAULT 1," +
                NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE + " TEXT NOT NULL)";
        Log.d(TAG, "onCreate: " + SQL_CREATE_NOTES_TABLE);

        db.execSQL(SQL_CREATE_NOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotesContract.NoteEntry.TABLE_NAME);
        onCreate(db);
    }
}
