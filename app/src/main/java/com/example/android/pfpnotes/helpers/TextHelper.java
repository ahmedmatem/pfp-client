package com.example.android.pfpnotes.helpers;

/**
 * Created by ahmed on 16/02/2018.
 */

public class TextHelper {

    /**
     *
     * @param date format 23/01/2018 10:41
     * @return 23/01/2018
     */
    public static String getDDMMYYYY(String date) {
        return date.substring(0, 10);
    }
}
