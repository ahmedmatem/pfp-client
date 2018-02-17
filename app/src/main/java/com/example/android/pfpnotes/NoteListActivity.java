package com.example.android.pfpnotes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.pfpnotes.adapters.NotesAdapter;
import com.example.android.pfpnotes.data.NotesContract;
import com.example.android.pfpnotes.helpers.TextHelper;

public class NoteListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int NOTE_LIST_LOADER = 0x01;

    private static final String TAG = "NoteListActivity";

    private TextView mDate;
    private TextView mTotalPerDay;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteListActivity.this, NoteAddActivity.class);
                startActivity(intent);
            }
        });

        mDate = (TextView) findViewById(R.id.tv_date);
        mTotalPerDay = (TextView) findViewById(R.id.tv_total);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_notes);
        getSupportLoaderManager().initLoader(NOTE_LIST_LOADER, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Cursor cursor = getContentResolver().query(NotesContract.NoteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        Log.d(TAG, "onCreateLoader: " + cursor);
        return new CursorLoader(this,
                NotesContract.NoteEntry.CONTENT_URI,
                null,
                null,
                null,
                NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        NotesAdapter adapter = new NotesAdapter(data, getLayoutInflater(), this);
        mRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        updateUi(data);
    }

    private void updateUi(Cursor cursor) {
        Double total = calculateTotalPrice(cursor);
        mTotalPerDay.setText(String.format("£%.2f", total));

        cursor.moveToFirst();
        String date = cursor.getString(cursor.
                getColumnIndex(NotesContract.NoteEntry.COLUMN_PUBLISHED_DATE));
        mDate.setText(TextHelper.getDDMMYYYY(date));
    }

    private Double calculateTotalPrice(Cursor cursor) {
        if(cursor != null) {
            boolean hasItem = cursor.moveToFirst();
            String price;
            Double total = .0;
            while (hasItem) {
                price = cursor.getString(cursor.getColumnIndex(NotesContract.NoteEntry.COLUMN_PRICE));
                total += Double.valueOf(price);
                hasItem = cursor.moveToNext();
            }

            return total;
        }
        return 0d;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }
}
