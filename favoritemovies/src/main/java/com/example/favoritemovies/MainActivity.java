package com.example.favoritemovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.favoritemovies.adapter.FavoriteAdapter;

import static com.example.favoritemovies.database.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    FavoriteAdapter adapter;
    ListView listView;
    private final int LOAD_NOTES_ID = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new FavoriteAdapter(this, null, true);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new android.support.v4.content.CursorLoader(this, CONTENT_URI, null, null, null, null);
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}
