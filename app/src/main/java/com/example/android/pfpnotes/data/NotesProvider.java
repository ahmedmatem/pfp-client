package com.example.android.pfpnotes.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.SQLData;

/**
 * Created by ahmed on 13/02/2018.
 */

public class NotesProvider extends ContentProvider {
    private NotesDbHelper mDbHelper;

    private static final int NOTES = 100;
    private static final int NOTE_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = NotesContract.NOTES_AUTHORITY;
        // directory
        uriMatcher.addURI(authority, NotesContract.PATH_NOTES, NOTES);
        // individual
        uriMatcher.addURI(authority, NotesContract.PATH_NOTES + "/#", NOTE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new NotesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        Cursor returnCursor = null;
        switch (match) {
            case NOTES:
                returnCursor = db.query(NotesContract.NoteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case NOTE_WITH_ID:
                // Uri: content://<authority>/notes/#
                String id = uri.getPathSegments().get(1);
                // selection is the _ID column = ?
                // and the Selection args = the row ID from the Uri
                String mSelection = NotesContract.NoteEntry._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                returnCursor = db.query(NotesContract.NoteEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }

        // set a notification Uri on the Cursor
        if (returnCursor != null) {
            returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case NOTES:
                return NotesContract.NoteEntry.CONTENT_TYPE;
            case NOTE_WITH_ID:
                return NotesContract.NoteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case NOTES:
                long id = db.insert(NotesContract.NoteEntry.TABLE_NAME,
                        null,
                        values);
                if (id > 0) {
                    // success
                    returnUri = ContentUris.withAppendedId(
                            NotesContract.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numberOfDeletedRows;
        switch (match) {
            case NOTE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = NotesContract.NoteEntry._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                numberOfDeletedRows = db.delete(NotesContract.NoteEntry.TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return numberOfDeletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numberOfUpdatedRows;
        switch (match) {
            case NOTE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSelection = NotesContract.NoteEntry._ID + "=?";
                String[] mSelectionArgs = new String[]{id};
                numberOfUpdatedRows = db.update(NotesContract.NoteEntry.TABLE_NAME,
                        values,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return numberOfUpdatedRows;
    }
}
