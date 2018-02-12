package com.example.android.pfpnotes.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.android.pfpnotes.R;

/**
 * Created by ahmed on 12/02/2018.
 */

public class ShapeSpinnerAdapter extends BaseAdapter {
    private Context mContext;

    public ShapeSpinnerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2; // rectangle and line
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
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }
        if(position == 0) { // hole
            imageView.setImageDrawable(mContext.getResources()
            .getDrawable(R.drawable.ic_rectangle));
        } else if(position == 1) { // line
            imageView.setImageDrawable(mContext.getResources()
                    .getDrawable(R.drawable.ic_line));
        }

        return imageView;
    }
}
