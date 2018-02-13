package com.example.android.pfpnotes.data;

import android.provider.BaseColumns;

/**
 * Created by ahmed on 13/02/2018.
 */

public final class NotesContract {

    private NotesContract() {}

    public static class NoteEntry implements BaseColumns{
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_SHAPE = "shape";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_SKIN = "skin";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";
    }


}
