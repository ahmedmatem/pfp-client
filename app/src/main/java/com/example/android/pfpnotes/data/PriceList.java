package com.example.android.pfpnotes.data;

import com.example.android.pfpnotes.models.Interval;

import java.util.ArrayList;

/**
 * Created by ahmed on 14/02/2018.
 */

public class PriceList {

    private static final double MAX_PRICE = 29.80;

    // values are in m2
    private static final ArrayList<Interval> mIntervals = new ArrayList<Interval>(){{
        add(new Interval(0.00, 0.01, 2.60));
        add(new Interval(0.011, 0.05, 4.20));
        add(new Interval(0.051, 0.10, 7.70));
        add(new Interval(0.11, 0.20, 9.30));
        add(new Interval(0.21, 0.30, 11.20));
        add(new Interval(0.31, 0.40, 13.30));
        add(new Interval(0.41, 0.50, 15.50));
        add(new Interval(0.51, 0.60, 17.20));
        add(new Interval(0.61, 0.70, 19.90));
        add(new Interval(0.71, 0.80, 22.30));
        add(new Interval(0.81, 0.90, 24.20));
        add(new Interval(0.91, 1.00, 27.60));
        add(new Interval(1.0001, Double.MAX_VALUE, MAX_PRICE));
    }};

    /**
     *
     * @param number
     * @return interval or null if no interval
     */
    public static Interval getIntervalContains(double number){
        for(Interval interval : mIntervals){
            if(interval.contains(number))
                return interval;
        }
        return null;
    }

    public static boolean isLastInterval(double price){
        if(price == MAX_PRICE){
            return true;
        }
        return  false;
    }
}
