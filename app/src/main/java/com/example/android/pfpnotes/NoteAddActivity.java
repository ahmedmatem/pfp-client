package com.example.android.pfpnotes;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.pfpnotes.data.PlaceAdapter;
import com.example.android.pfpnotes.data.ShapeSpinnerAdapter;
import com.example.android.pfpnotes.ui.NumberPickerFragment;

public class NoteAddActivity extends AppCompatActivity
        implements NumberPickerFragment.OnFragmentInteractionListener {
    private static final String TAG = "NoteAddActivity";
    private int mGridViewClickedItemPosition = -1;

    private FragmentManager mFragmentManager;

    private NumberPickerFragment mWidth;
    private NumberPickerFragment mHight;
    private TextView mSummary;
    private Switch mSwitchTwoSkin;

    private boolean isDone = false; // indicate whether "done" action button is visible
    private boolean isPlaceSelected = false;
    private boolean isTwoSkinChecked = false;
    private String mSummaryTextPlace;
    private int mSpinnerSelectedItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        GridView places = (GridView) findViewById(R.id.gv_places);
        places.setAdapter(new PlaceAdapter(this));
        places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGridViewClickedItemPosition == position)
                    return;

                isPlaceSelected = true;

                TextView prevClickedItem = null;
                if (mGridViewClickedItemPosition != -1) {
                    prevClickedItem =
                            (TextView) parent.getChildAt(mGridViewClickedItemPosition);
                }
                TextView currentClickedItem = (TextView) parent.getChildAt(position);
                refreshGridView(prevClickedItem, currentClickedItem);

                mSummaryTextPlace = currentClickedItem.getText().toString();

                mGridViewClickedItemPosition = position;

                updateUI();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> data = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,
//                new String[]{"Hole", "Line"});
        ShapeSpinnerAdapter spinnerAdapter = new ShapeSpinnerAdapter(this);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerSelectedItemPosition = position;
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFragmentManager = getSupportFragmentManager();
        mWidth = (NumberPickerFragment) mFragmentManager
                .findFragmentById(R.id.width_number_picker);
        mHight = (NumberPickerFragment) mFragmentManager
                .findFragmentById(R.id.hight_number_picker);

        mSwitchTwoSkin = (Switch) findViewById(R.id.switch_two_skin);
        mSwitchTwoSkin.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTwoSkinChecked = isChecked;
                updateUI();
            }
        });

        mSummary = (TextView) findViewById(R.id.tv_summary);

    }

    private void updateUI() {
        switch (mSpinnerSelectedItemPosition) {
            case 0: // Hole
                mFragmentManager.beginTransaction()
                        .show(mHight)
                        .commit();
                mSwitchTwoSkin.setVisibility(View.VISIBLE);

                if (isPlaceSelected &&
                        mWidth.getNumber() != 0 &&
                        mHight.getNumber() != 0) {
                    isDone = true;
                } else {
                    isDone = false;
                }
                break;
            case 1: // Line
                mFragmentManager.beginTransaction()
                        .hide(mHight)
                        .commit();
                mSwitchTwoSkin.setVisibility(View.GONE);

                if (isPlaceSelected &&
                        mWidth.getNumber() != 0) {
                    isDone = true;
                } else {
                    isDone = false;
                }
                break;
        }

        updateSummary();
        // next line call onPrepareOptionsMenu
        invalidateOptionsMenu();
    }

    private void updateSummary() {
        if (isDone) {
            StringBuilder summaryText = new StringBuilder();
            summaryText.append(mSummaryTextPlace + ", ");
            summaryText.append(mWidth.getNumber());
            if(mSpinnerSelectedItemPosition == 0){ // hole
                summaryText.append(" x ");
                summaryText.append(mHight.getNumber());
                if(isTwoSkinChecked){
                    summaryText.append(" x 2");
                }
                summaryText.append(" (cm)");
                // add square line
                summaryText.append("\n");
                double s = mWidth.getNumber() * mHight.getNumber();
                summaryText.append("S = " + s / 10000 + " (m2)");
                // add price line
                summaryText.append("\n");
                summaryText.append("Price: " + "price goes here");
            } else { // line
                summaryText.append(" (cm)");
            }
            mSummary.setText(summaryText.toString());
            mSummary.setVisibility(View.VISIBLE);
        } else {
            mSummary.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_done:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_done).setVisible(isDone);
        return super.onPrepareOptionsMenu(menu);
    }

    private void refreshGridView(TextView prevClickedItem, TextView currentClickedItem) {
        if (prevClickedItem != null) {
            prevClickedItem.setBackground(getResources().getDrawable(R.drawable.rect_border));
        }
        currentClickedItem.setBackgroundColor(getResources().getColor(R.color.pink_a200));
    }

    @Override
    public void onNumberPickerValueChanged() {
        updateUI();
    }
}
