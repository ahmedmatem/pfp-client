package com.example.android.pfpnotes.models;

/**
 * Created by ahmed on 16/02/2018.
 */

public abstract class Item {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NOTE = 1;

    public abstract int getType();
}
