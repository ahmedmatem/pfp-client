package com.example.android.pfpnotes;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pfpnotes.adapters.PlaceAdapter;
import com.example.android.pfpnotes.adapters.ShapeSpinnerAdapter;
import com.example.android.pfpnotes.asynctasks.SaveData;
import com.example.android.pfpnotes.data.NotesContract;
import com.example.android.pfpnotes.data.PriceList;
import com.example.android.pfpnotes.models.Interval;
import com.example.android.pfpnotes.ui.NoteSaveDialogFragment;
import com.example.android.pfpnotes.ui.NumberPickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteAddActivity extends AppCompatActivity
        implements NumberPickerFragment.OnFragmentInteractionListener, SaveData.OnDataSaved{
    private static final String TAG = "NoteAddActivity";
    private int mGridViewClickedItemPosition = -1;

    private FragmentManager mFragmentManager;

    private NumberPickerFragment mWidth;
    private NumberPickerFragment mHeight;
    private TextView mSummary;
    private Switch mSwitchTwoSkin;

    private boolean isDone = false; // indicate whether "done" action button is visible
    private boolean isPlaceSelected = false;
    private boolean isTwoSkinChecked = false;
    private String mPlace;
    private int mSpinnerSelectedItemPosition;
    private double mPrice;

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

                mPlace = currentClickedItem.getText().toString();

                mGridViewClickedItemPosition = position;

                updateUI();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ShapeSpinnerAdapter adapter = new ShapeSpinnerAdapter(this);
        spinner.setAdapter(adapter);
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
        mHeight = (NumberPickerFragment) mFragmentManager
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
                        .show(mHeight)
                        .commit();
                mSwitchTwoSkin.setVisibility(View.VISIBLE);

                if (isPlaceSelected &&
                        mWidth.getNumber() != 0 &&
                        mHeight.getNumber() != 0) {
                    isDone = true;
                } else {
                    isDone = false;
                }
                break;
            case 1: // Line
                mFragmentManager.beginTransaction()
                        .hide(mHeight)
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
            summaryText.append(mPlace + ", ");
            summaryText.append(mWidth.getNumber());
            if (mSpinnerSelectedItemPosition == 0) { // hole
                summaryText.append(" x ");
                summaryText.append(mHeight.getNumber());
                if (isTwoSkinChecked) {
                    summaryText.append(" x 2");
                }
                summaryText.append(" (cm)");
                // add square line
                summaryText.append("\n");
                double s_cm2 = mWidth.getNumber() * mHeight.getNumber(); // cm2
                summaryText.append("S = " + (s_cm2 / 10000) + " (m2)"); // cm2 -> m2
                // add price line
                summaryText.append("\n");
                Interval interval = PriceList.getIntervalContains(s_cm2 / 10000); // cm2 - > m2
                if (interval != null) {
                    mPrice = interval.getPrice();
                    if (PriceList.isLastInterval(mPrice)) {
                        mPrice *= s_cm2 / 10000;
                    }
                    if (isTwoSkinChecked) {
                        mPrice *= 2;
                    }
                    summaryText.append("Price: £" + String.format("%.2f", mPrice));
                }
            } else { // line
                summaryText.append(" (cm)");
                summaryText.append("\n");
                mPrice = Double.valueOf(mWidth.getNumber()) / 100; // sm -> m
                summaryText.append("Price: £" + String.format("%.2f", mPrice));
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
                saveData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveData() {
        ContentValues values = new ContentValues();
        values.put(NotesContract.NoteEntry.COLUMN_PLACE, mPlace);
        values.put(NotesContract.NoteEntry.COLUMN_SHAPE, mSpinnerSelectedItemPosition);
        values.put(NotesContract.NoteEntry.COLUMN_WIDTH, mWidth.getNumber());
        if (mSpinnerSelectedItemPosition == 0) { // rectangle shape
            values.put(NotesContract.NoteEntry.COLUMN_HEIGHT, mHeight.getNumber());
            if (isTwoSkinChecked) {
                values.put(NotesContract.NoteEntry.COLUMN_SKIN, 2);
            }
        }
        values.put(NotesContract.NoteEntry.COLUMN_PRICE, mPrice);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(getString(R.string.date_format));
        String date = simpleDateFormat.format(new Date());
        values.put(NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE, date);
        Log.d(TAG, "saveData: " + values);
        new SaveData(this).execute(values);
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

    @Override
    public void onDataSaved(Uri uri) {
        Toast.makeText(this, R.string.note_successfully_added, Toast.LENGTH_SHORT).show();
//        crate dialog
//        new NoteSaveDialogFragment().show(getSupportFragmentManager(), "note_save");
        Intent intent = new Intent(this, NoteListActivity.class);
        startActivity(intent);
    }
}
