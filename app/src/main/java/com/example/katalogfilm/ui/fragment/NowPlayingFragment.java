package com.example.katalogfilm.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.loopj.MyAsyncTaskLoaderNowPlayingMovie;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.ui.activity.DetailMovieActivity;
import com.example.katalogfilm.util.ViewOnItemClick;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    MovieAdapter adapter;
    RecyclerView listMovie;
    private String TAG = "hasil";
    private String language;


    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);


        Log.e(TAG, "onCreateView: ");
        listMovie = view.findViewById(R.id.rv_nowplaying);

        adapter = new MovieAdapter(getContext());
        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);

        language = getResources().getString(R.string.bahasa);
        getLoaderManager().initLoader(0, savedInstanceState, this);

        setOnClick();
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {

        return new MyAsyncTaskLoaderNowPlayingMovie(getActivity(), language);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    public void setOnClick() {

        adapter.setCallback(new ViewOnItemClick.MovieItemCallback() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent(getContext(), DetailMovieActivity.class);
                intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
