package com.example.katalogfilm.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.katalogfilm.data.model.MovieItems;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.ID_FILM;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.IMAGE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.LANGUAGE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.RATING;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.RELEASE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.TITLE;
import static com.example.katalogfilm.data.database.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_MOVIE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public ArrayList<MovieItems> query() {
        ArrayList<MovieItems> arrayList = new ArrayList<MovieItems>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);
        cursor.moveToFirst();
        MovieItems movieItems;
        if (cursor.getCount() > 0) {
            do {

                movieItems = new MovieItems();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_FILM)));
                movieItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movieItems.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(IMAGE)));
                movieItems.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(RATING)));
                movieItems.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                movieItems.setPopularity(cursor.getInt(cursor.getColumnIndexOrThrow(POPULARITY)));
                movieItems.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));

                arrayList.add(movieItems);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , ID_FILM + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String title, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID_FILM + " = ?", new String[]{title});
    }

    public int deleteProvider(String title) {
        return database.delete(DATABASE_TABLE, ID_FILM + " = ?", new String[]{title});
    }
}
