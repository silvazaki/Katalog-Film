package com.example.favoritemovies.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favoritemovies.R;
import com.squareup.picasso.Picasso;

import static com.example.favoritemovies.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.example.favoritemovies.database.DatabaseContract.MovieColumns.IMAGE;
import static com.example.favoritemovies.database.DatabaseContract.MovieColumns.TITLE;
import static com.example.favoritemovies.database.DatabaseContract.getColumnString;

/**
 * Created by User on 1/28/2019.
 */

public class CursorFavoriteAdapter extends CursorAdapter {

    public CursorFavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_items, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle, tvDescription;
        ImageView imgCover;
        if (cursor != null){
            tvTitle = view.findViewById(R.id.tv_item_name);
            tvDescription = view.findViewById(R.id.tv_item_desc);
            imgCover = view.findViewById(R.id.img_item_photo);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvDescription.setText(getColumnString(cursor,DESCRIPTION));
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w185" +getColumnString(cursor,IMAGE))
                    .into(imgCover);
//            Log.e("hasil", "bindView: "+"https://image.tmdb.org/t/p/w185" +getColumnString(cursor,IMAGE) );
        }
    }
}