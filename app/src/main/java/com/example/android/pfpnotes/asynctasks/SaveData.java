package com.example.android.pfpnotes.asynctasks;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.pfpnotes.data.NotesContract;

/**
 * Created by ahmed on 13/02/2018.
 */

public class SaveData extends AsyncTask<ContentValues, Void, Uri> {
    private static final String TAG = "SaveData";

    public interface OnDataSaved {
        void onDataSaved(Uri uri);
    }

    private Context mContext;

    public SaveData(Context context) {
        mContext = context;
    }

    @Override
    protected Uri doInBackground(ContentValues... cvs) {
        ContentValues values = cvs[0];
        Uri uri = mContext.getContentResolver()
                .insert(NotesContract.NoteEntry.CONTENT_URI, values);
        return uri;
    }

    @Override
    protected void onPostExecute(Uri uri) {
        if(mContext != null) {
            ((OnDataSaved) mContext).onDataSaved(uri);
        }
    }
}
