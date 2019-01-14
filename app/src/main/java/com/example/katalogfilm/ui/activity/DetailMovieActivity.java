package com.example.katalogfilm.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.model.MovieItems;
import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "MOVIE_DETAIL";
    ImageView imgDetail;
    TextView title, rating, language, release, overview, popular;
    MovieItems items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        items = getIntent().getParcelableExtra(MOVIE_DETAIL);

        if (items != null) {
            getSupportActionBar().setTitle(items.getTitle());
        }

        imgDetail = findViewById(R.id.img_detail);
        title = findViewById(R.id.tv_title);
        rating = findViewById(R.id.tv_rating);
        popular = findViewById(R.id.tv_popularity);
        language = findViewById(R.id.tv_language);
        release = findViewById(R.id.tv_release);
        overview = findViewById(R.id.tv_overview);

        setUp();
    }

    void setUp() {
        Picasso.with(DetailMovieActivity.this).load(items.getPosterPath()).into(imgDetail);
        title.setText(items.getTitle());
        popular.setText(String.valueOf(items.getPopularity()));
        rating.setText(String.valueOf(items.getVoteAverage()));
        language.setText(items.getOriginalLanguage().toUpperCase());
        release.setText(items.getReleaseDate());
        overview.setText(items.getOverview());
    }
}
