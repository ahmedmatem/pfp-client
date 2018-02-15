package com.example.android.pfpnotes.data;

import android.media.session.PlaybackState;

import com.example.android.pfpnotes.models.Place;

/**
 * Created by ahmed on 15/02/2018.
 */

public class Data {
    public static final Place[] sPlaces = new Place[]{
            new Place("Basement", "B"),
            new Place("Ground floor", "GF"),
            new Place("Level 1", "L1"),
            new Place("Level 2", "L2"),
            new Place("Level 3", "L3"),
            new Place("Level 4", "L4"),
            new Place("Level 5", "L5"),
            new Place("Stairs Basement", "SB"),
            new Place("Stairs Ground", "SG"),
            new Place("Stairs 1", "S1"),
            new Place("Stairs 2", "S2"),
            new Place("Stairs 3", "S3"),
            new Place("Stairs 4", "S4"),
            new Place("Stairs 5", "S5"),
    };

    public static String getPlaceName(String shortName){
        for(Place place : sPlaces){
            if(place.getShortName().equals(shortName)){
                return place.getName();
            }
        }
        return null;
    }
}
