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
import com.example.android.pfpnotes.helpers.TextHelper;
import com.example.android.pfpnotes.models.HeaderItem;
import com.example.android.pfpnotes.models.Item;
import com.example.android.pfpnotes.models.NoteItem;

import java.util.ArrayList;

/**
 * Created by ahmed on 13/02/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "NotesAdapter";
    Context mContext;
    private Cursor mCursor;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Item> mData = null;

    public NotesAdapter(Cursor cursor, LayoutInflater layoutInflater, Context context) {
        mCursor = cursor;
        mData = extractDataFromCursor(mCursor);
        mLayoutInflater = layoutInflater;
        mContext = context;
        Log.d(TAG, "NotesAdapter: " + mCursor);
    }

    private ArrayList<Item> extractDataFromCursor(Cursor cursor) {
        if (mCursor != null) {
            mData = new ArrayList<>();
            String date = "";
            String currentDate;
            while (mCursor.moveToNext()) {
                currentDate = mCursor.getString(
                        mCursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE));
                currentDate = TextHelper.getDDMMYYYY(currentDate);
                if (!currentDate.equals(date)) {
                    HeaderItem item = new HeaderItem(currentDate);
                    mData.add(item);
                    date = currentDate;
                } else {
                    NoteItem item = new NoteItem(mCursor);
                    mData.add(item);
                }
            }
            Log.d(TAG, "extractDataFromCursor: " + mData.size());
            return mData;
        }

        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Item.TYPE_HEADER) {
            View view = mLayoutInflater.inflate(R.layout.note_list_header, parent, false);
            NoteListHeaderViewHolder viewHolder = new NoteListHeaderViewHolder(view);
            return viewHolder;
        } else {
            View view = mLayoutInflater.inflate(R.layout.note_list_item, parent, false);
            NoteViewHolder viewHolder = new NoteViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case Item.TYPE_HEADER:
                HeaderItem header = (HeaderItem) mData.get(position);
                NoteListHeaderViewHolder headerHolder = (NoteListHeaderViewHolder) holder;
                headerHolder.mDate.setText(header.getDate());
                break;
            case Item.TYPE_NOTE:
                NoteItem note = (NoteItem) mData.get(position);
                NoteViewHolder noteHolder = (NoteViewHolder) holder;

                // set shape
                int shape = 0;
                Drawable drawable = null;
                switch (note.getShape()) {
                    case 0:
                        shape = 0;
                        drawable = mContext.getResources().getDrawable(R.drawable.ic_rectangle);
                        break;
                    case 1:
                        shape = 1;
                        drawable = mContext.getResources().getDrawable(R.drawable.ic_line);
                        break;
                }
                noteHolder.mShape.setImageDrawable(drawable);

                // set place
                String placeName = Data.getPlaceName(note.getPlace());
                if (placeName != null) {
                    noteHolder.mPlace.setText(placeName);
                }

                // set width, height and skin
                int width = note.getWidth();
                int height = note.getHeight();
                int skin = note.getSkin();

                // set size
                StringBuilder size = new StringBuilder();
                size.append(width);
                if (shape == 0) { // rectangle
                    size.append(" x " + height);
                    if (skin == 2) {
                        size.append(" x 2 ");
                    }
                }
                size.append(" (cm)");
                noteHolder.mSize.setText(size.toString());

                // set price
                double price = note.getPrice();
                noteHolder.mPrice.setText(String.format("£%.2f", price));

                break;
            default:
                throw new UnsupportedOperationException("Undefine view type " + viewType);
        }
    }

    @Override
    public int getItemCount() {

//        return (mCursor != null) ? mCursor.getCount() : 0;
        return (mData != null) ? mData.size() : 0;
    }

    @Override
    public long getItemId(int position) {
//        mCursor.moveToPosition(position);
//        return mCursor.getLong(mCursor.getColumnIndex(NotesContract.NoteEntry._ID));
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }
}

