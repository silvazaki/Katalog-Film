package com.example.katalogfilm.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.database.MovieHelper;
import com.example.katalogfilm.data.model.MovieItems;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.katalogfilm.data.database.DatabaseContract.CONTENT_URI;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.DESCRIPTION;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.ID_FILM;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.IMAGE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.LANGUAGE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.POPULARITY;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.RATING;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.RELEASE;
import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.TITLE;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.img_detail)
    ImageView imgDetail;
    @BindView(R.id.img_fav)
    ImageView imgFavorite;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_rating)
    TextView rating;
    @BindView(R.id.tv_popularity)
    TextView popular;
    @BindView(R.id.tv_language)
    TextView language;
    @BindView(R.id.tv_release)
    TextView release;
    @BindView(R.id.tv_overview)
    TextView overview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";
    private static String TAG = "hasil-detail";
    private boolean isEdit = false;
    private MovieItems items;
    private MovieItems movieItems;
    private MovieHelper movieHelper;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        items = getIntent().getParcelableExtra(MOVIE_DETAIL);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle(items.getTitle());
        }

        title.setText(items.getTitle());
        overview.setText(items.getOverview());
        Picasso.with(DetailMovieActivity.this).load("https://image.tmdb.org/t/p/w185" + items.getPosterPath()).into(imgDetail);
        if (items.getOriginalLanguage() != null) {
            popular.setText(String.valueOf(items.getPopularity()));
            rating.setText(String.valueOf(items.getVoteAverage()));
            language.setText(items.getOriginalLanguage().toUpperCase());
            release.setText(items.getReleaseDate());
        }

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    movieItems = new MovieItems(cursor);
                }
                cursor.close();
            }
        }
        if (movieItems != null) {
            isEdit = true;
            imgFavorite.setColorFilter(getResources().getColor(R.color.colorRed));
        } else {
            isEdit = false;
            imgFavorite.setColorFilter(getResources().getColor(R.color.colorWhite));
        }

        if (items.getOriginalLanguage() == null)
            setUp();

        setOnClick();
    }

    void setOnClick() {
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit) {
                    getContentResolver().delete(uri, null, null);
                    isEdit = false;
                    imgFavorite.setColorFilter(getResources().getColor(R.color.colorWhite));
                    Log.e(TAG, "onClick: true");
                } else {
                    ContentValues values = new ContentValues();
                    values.put(ID_FILM, items.getId());
                    values.put(TITLE, items.getTitle());
                    values.put(DESCRIPTION, items.getOverview());
                    values.put(IMAGE, items.getPosterPath());
                    values.put(RATING, items.getVoteAverage());
                    values.put(POPULARITY, String.valueOf(items.getPopularity()));
                    values.put(RELEASE, items.getReleaseDate());
                    values.put(LANGUAGE, items.getOriginalLanguage());

                    getContentResolver().insert(CONTENT_URI, values);

                    isEdit = true;
                    imgFavorite.setColorFilter(getResources().getColor(R.color.colorRed));

                    Log.e(TAG, "onClick: false");
                }
            }
        });
    }

    void setUp() {
        Picasso.with(DetailMovieActivity.this).load(movieItems.getPosterPath()).into(imgDetail);
        popular.setText(String.valueOf(movieItems.getPopularity()));
        rating.setText(String.valueOf(movieItems.getVoteAverage()));
        language.setText(movieItems.getOriginalLanguage().toUpperCase());
        release.setText(movieItems.getReleaseDate());
    }


}
