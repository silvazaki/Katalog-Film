package com.example.katalogfilm.data.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.katalogfilm";
    public static String TABLE_MOVIE = "movie";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class MovieColumns implements BaseColumns {
        public static String ID_FILM = "id_film";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String IMAGE = "image";
        public static String RATING = "vote";
        public static String POPULARITY = "popularity";
        public static String RELEASE = "release_date";
        public static String LANGUAGE = "language";
    }

}