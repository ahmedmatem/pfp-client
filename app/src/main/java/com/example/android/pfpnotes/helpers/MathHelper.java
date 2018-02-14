package com.example.android.pfpnotes.helpers;

import java.text.DecimalFormat;

/**
 * Created by ahmed on 14/02/2018.
 */

public class MathHelper {

    public static double round(double value, int places){
        double factor = Math.pow(10, places);
        value = value * factor;
        value = Math.round(value);
        return (double) value / factor;
    }
}
