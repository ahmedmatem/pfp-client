package com.example.android.pfpnotes.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ahmed on 13/02/2018.
 */

public final class NotesContract {
    public static final String NOTES_AUTHORITY = "com.example.android.pfpnotes";

    public static final Uri CONTENT_URI =
            Uri.parse("content://com.example.android.pfpnotes");

    public static final String PATH_NOTES = "notes";

    private NotesContract() {
    }

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_SHAPE = "shape";
        public static final String COLUMN_WIDTH = "width";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_SKIN = "skin";
        public static final String COLUMN_PUBLISHED_DATE = "published_date";
    }


}
