package com.example.android.pfpnotes.models;

/**
 * Created by ahmed on 16/02/2018.
 */

public class HeaderItem extends Item {
    private String mDate;
    private Double mTotal;

    public HeaderItem(String date) {
        mDate = date;
    }

    public HeaderItem(String date, Double totalPrice) {
        this(date);
        mTotal = totalPrice;
    }

    @Override
    public int getType() {
        return Item.TYPE_HEADER;
    }

    public String getDate() {
        return mDate;
    }

    public Double getTotal() {
        return mTotal;
    }

    public void setTotal(Double total) {
        mTotal = total;
    }
}
