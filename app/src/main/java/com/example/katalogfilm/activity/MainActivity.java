package com.example.katalogfilm.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.katalogfilm.R;
import com.example.katalogfilm.utils.ViewOnItemClick;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.loopj.MyAsyncTaskLoaderSearchMovie;
import com.example.katalogfilm.data.model.MovieItems;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    MovieAdapter adapter;
    EditText editTextPencarian;
    Button buttonCari;
    RecyclerView listMovie;

    static final String EXTRAS_FILM = "EXTRAS_FILM";
    private String TAG = "hasil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        editTextPencarian = findViewById(R.id.ed_cari);
        buttonCari = findViewById(R.id.btn_cari);
        listMovie = findViewById(R.id.listView);

        adapter = new MovieAdapter(this);
        listMovie.setLayoutManager(new LinearLayoutManager(this));
        listMovie.setAdapter(adapter);

        String query = "captain";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM,query);

        getLoaderManager().initLoader(0, bundle, this);

        setOnClick();
    }


    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String query = "";
        if (bundle != null){
            query = bundle.getString(EXTRAS_FILM);
        }

        Log.d(TAG, "onCreateLoader: ");
        return new MyAsyncTaskLoaderSearchMovie(this,query);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        Log.e(TAG, "onLoadFinished: "+movieItems.get(0).getTitle());
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {

        adapter.setData(null);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setOnClick(){


        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = editTextPencarian.getText().toString();

                if (TextUtils.isEmpty(query))return;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM,query);
                getLoaderManager().restartLoader(0,bundle,MainActivity.this);
            }
        });

        adapter.setCallback(new ViewOnItemClick.MovieItemCallback() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }


}
