package com.example.android.pfpnotes.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.pfpnotes.R;
import com.example.android.pfpnotes.data.Data;
import com.example.android.pfpnotes.data.NotesContract;

/**
 * Created by ahmed on 13/02/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private static final String TAG = "NotesAdapter";
    Context mContext;
    private Cursor mCursor;
    private LayoutInflater mLayoutInflater;

    public NotesAdapter(Cursor cursor, LayoutInflater layoutInflater, Context context) {
        mCursor = cursor;
        mLayoutInflater = layoutInflater;
        mContext = context;
        Log.d(TAG, "NotesAdapter: " + mCursor);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.note_list_item, parent, false);
        NoteViewHolder viewHolder = new NoteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int shape = 0;
        Drawable drawable = null;
        switch (mCursor.getInt(mCursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_SHAPE))) {
            case 0: // rectangle
                shape = 0;
                drawable = mContext.getResources().getDrawable(R.drawable.ic_rectangle);
                break;
            case 1: // line
                shape = 1;
                drawable = mContext.getResources().getDrawable(R.drawable.ic_line);
                break;
        }
        holder.mShape.setImageDrawable(drawable);

        String place = mCursor.getString(mCursor.getColumnIndex(
                NotesContract.NoteEntry.COLUMN_PLACE));
        String placeName = Data.getPlaceName(place);
        if(placeName != null) {
            holder.mPlace.setText(placeName);
        }
        int width = mCursor.getInt(mCursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_WIDTH));
        int height = mCursor.getInt(mCursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_HEIGHT));
        int skin = mCursor.getInt(mCursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_SKIN));

        StringBuilder size = new StringBuilder();
        size.append(width);
        if (shape == 0) { // rectangle
            size.append(" x " + height);
            if (skin == 2) {
                size.append(" x 2 ");
            }
        }
        size.append(" (cm)");

        holder.mSize.setText(size.toString());
        double price = mCursor.getDouble(mCursor.getColumnIndex(
                NotesContract.NoteEntry.COLUMN_PRICE));
        holder.mPrice.setText(String.format("£%.2f", price));
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(mCursor.getColumnIndex(NotesContract.NoteEntry._ID));
    }
}

