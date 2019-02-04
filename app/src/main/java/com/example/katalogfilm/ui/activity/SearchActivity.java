package com.example.katalogfilm.ui.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.data.network.MyAsyncTaskLoaderSearchMovie;
import com.example.katalogfilm.util.ViewOnItemClick;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    MovieAdapter adapter;
    RecyclerView listMovie;
    SearchView searchView;
    TextView searchInfo;
    private String language;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        listMovie = findViewById(R.id.listView);
        searchView = findViewById(R.id.search_view);
        searchInfo = findViewById(R.id.tv_search_info);

        adapter = new MovieAdapter(this);
        listMovie.setLayoutManager(new LinearLayoutManager(this));
        listMovie.setAdapter(adapter);
        language = getResources().getString(R.string.bahasa);
        setOnClick();
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String query = "";
        if (args != null) {
            query = args.getString(EXTRAS_FILM);
        }

        return new MyAsyncTaskLoaderSearchMovie(this, query, language);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        if (movieItems.size() != 0) {
            adapter.setData(movieItems);
            searchInfo.setText(String.format(getResources().getString(R.string.searcht), movieItems.size(), query));
        } else {
            searchInfo.setText(String.format(getResources().getString(R.string.seaarchf), query));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
    }

    public void setOnClick() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)) {
                    query = s;
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_FILM, s);
                    getLoaderManager().restartLoader(0, bundle, SearchActivity.this);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        adapter.setCallback(new ViewOnItemClick.MovieItemCallback() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getApplicationContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

}
