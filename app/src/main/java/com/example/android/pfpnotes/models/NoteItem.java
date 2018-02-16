package com.example.android.pfpnotes.models;

import android.database.Cursor;

import com.example.android.pfpnotes.data.NotesContract;

/**
 * Created by ahmed on 16/02/2018.
 */

public class NoteItem extends Item {

    private int mId;
    private String mPlace;
    private int mShape; // 0- rectangle, 1 - line
    private int mWidth; // cm
    private int mHeight; // cm
    private int mSkin;
    private double mPrice;
    private String mPublishedDate;

    public NoteItem(Cursor cursor) {
        mId = cursor.getInt(cursor
                .getColumnIndex(NotesContract.NoteEntry._ID));
        mPlace = cursor.getString(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_PLACE));
        mShape = cursor.getInt(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_SHAPE));
        mWidth = cursor.getInt(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_WIDTH));
        mHeight = cursor.getInt(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_HEIGHT));
        mSkin = cursor.getInt(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_SKIN));
        mPrice = cursor.getDouble(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_PRICE));
        mPublishedDate = cursor.getString(cursor
                .getColumnIndex(NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE));
    }

    public int getId() {
        return mId;
    }

    public String getPlace() {
        return mPlace;
    }

    public int getShape() {
        return mShape;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getSkin() {
        return mSkin;
    }

    public double getPrice() {
        return mPrice;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    @Override
    public int getType() {
        return Item.TYPE_NOTE;
    }
}
