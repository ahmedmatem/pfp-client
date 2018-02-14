package com.example.android.pfpnotes.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.pfpnotes.R;

/**
 * Created by ahmed on 10/02/2018.
 */

public class PlaceAdapter extends BaseAdapter {
    private static final String[] places = new String[]{
            "B", "GF", "L1", "L2", "L3", "L4", "L5",
            "SB", "SGF", "S1", "S2", "S3", "S4", "S5"
    };

    private Context mContext;

    public PlaceAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return places.length;
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
        place.setText(places[position]);

        return place;
    }
}
