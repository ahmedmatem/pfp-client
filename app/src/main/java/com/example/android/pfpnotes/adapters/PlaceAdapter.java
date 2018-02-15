package com.example.android.pfpnotes.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.pfpnotes.R;
import com.example.android.pfpnotes.models.Place;

/**
 * Created by ahmed on 10/02/2018.
 */

public class PlaceAdapter extends BaseAdapter {
    private static final Place[] sPlaces = new Place[]{
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

    private Context mContext;

    public PlaceAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return sPlaces.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView place;
        if(convertView == null){
            place = new TextView(mContext);
        } else {
            place = (TextView) convertView;
        }

        place.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        place.setLayoutParams(new ViewGroup
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 140));
        place.setBackground(mContext.getResources()
                .getDrawable(R.drawable.rect_border));
        place.setText(sPlaces[position].getShortName());

        return place;
    }
}
