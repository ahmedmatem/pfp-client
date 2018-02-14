package com.example.android.pfpnotes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pfpnotes.R;

/**
 * Created by ahmed on 13/02/2018.
 */

public class NoteViewHolder extends RecyclerView.ViewHolder {
    public ImageView mShape;
    public TextView mNote;
    public TextView mPrice;

    public NoteViewHolder(View itemView) {
        super(itemView);
        mShape = (ImageView) itemView.findViewById(R.id.iv_shape);
        mNote = (TextView) itemView.findViewById(R.id.tv_note);
        mPrice = (TextView) itemView.findViewById(R.id.tv_price);
    }
}
