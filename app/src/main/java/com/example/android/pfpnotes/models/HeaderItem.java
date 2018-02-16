package com.example.android.pfpnotes.models;

/**
 * Created by ahmed on 16/02/2018.
 */

public class HeaderItem extends Item {
    private String mDate;

    public HeaderItem(String date) {
        mDate = date;
    }

    @Override
    public int getType() {
        return Item.TYPE_HEADER;
    }

    public String getDate() {
        return mDate;
    }
}
