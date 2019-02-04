package com.example.katalogfilm.ui.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.data.network.MyAsyncTaskLoaderUpcomingMovie;
import com.example.katalogfilm.ui.activity.DetailMovieActivity;
import com.example.katalogfilm.util.ViewOnItemClick;

import java.util.ArrayList;

import static com.example.katalogfilm.data.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    MovieAdapter adapter;
    RecyclerView listMovie;
    ProgressDialog dialog;
    private String language;

    public UpcomingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        listMovie = view.findViewById(R.id.rv_upcoming);

        adapter = new MovieAdapter(getContext());
        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);

        language = getResources().getString(R.string.bahasa);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("loading...");
        dialog.show();
        getLoaderManager().initLoader(0, savedInstanceState, this);

        setOnClick();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = null;
        getLoaderManager().restartLoader(0, args, this);
    }


    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyAsyncTaskLoaderUpcomingMovie(getActivity(), language);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
        dialog.dismiss();
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
                Uri uri = Uri.parse(CONTENT_URI + "/" + adapter.getItem(position).getId());
                intent.putExtra(DetailMovieActivity.MOVIE_DETAIL, adapter.getItem(position));
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}
