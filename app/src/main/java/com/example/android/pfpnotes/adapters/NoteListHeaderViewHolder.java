package com.example.android.pfpnotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.pfpnotes.R;

/**
 * Created by ahmed on 16/02/2018.
 */

public class NoteListHeaderViewHolder extends RecyclerView.ViewHolder {
    public TextView mDate;
    public TextView mTotal;

    public NoteListHeaderViewHolder(View itemView) {
        super(itemView);
        mDate = (TextView) itemView.findViewById(R.id.tv_header);
        mTotal = (TextView) itemView.findViewById(R.id.tv_total_per_day);
    }
}
