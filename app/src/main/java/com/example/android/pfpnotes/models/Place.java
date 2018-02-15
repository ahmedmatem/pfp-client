package com.example.android.pfpnotes.models;

/**
 * Created by ahmed on 15/02/2018.
 */

public class Place {
    private String Name;
    private String ShortName;

    public Place(String name, String shortName) {
        Name = name;
        ShortName = shortName;
    }

    public String getName() {
        return Name;
    }

    public String getShortName() {
        return ShortName;
    }
}
