package com.example.android.pfpnotes.models;

import android.util.Log;

import com.example.android.pfpnotes.helpers.MathHelper;

import java.text.DecimalFormat;

/**
 * Created by ahmed on 14/02/2018.
 */

public class Interval {
    public static final int ROUND_PLACE = 2;

    private double minValue;
    private double maxValue;
    private double mPrice;

    public Interval(double minValue, double maxValue, double price) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.mPrice = price;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getPrice() {
        return mPrice;
    }

    public boolean contains(double number){
        number = MathHelper.round(number, ROUND_PLACE);
        if(minValue <= number  && number <= maxValue){
            return true;
        }
        return false;
    }
}
