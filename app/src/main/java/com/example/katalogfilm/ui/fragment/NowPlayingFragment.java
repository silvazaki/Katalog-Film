package com.example.katalogfilm.ui.fragment;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.data.network.MyAsyncTaskLoaderNowPlayingMovie;
import com.example.katalogfilm.ui.activity.DetailMovieActivity;
import com.example.katalogfilm.util.DialogHelper;
import com.example.katalogfilm.util.ViewOnItemClick;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.katalogfilm.data.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    @BindView(R.id.rv_nowplaying)
    RecyclerView listMovie;
    private Unbinder unbinder;

    private static final String LIST_STATE = "listState";
    private ArrayList<MovieItems> mListState = new ArrayList<>();

    MovieAdapter adapter;
    private String language;

    private AlertDialog loading;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new MovieAdapter(getContext());
        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);

        loading = DialogHelper.loading(getContext());

        language = getResources().getString(R.string.bahasa);

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(0, savedInstanceState, this);
        } else {
            mListState = savedInstanceState.getParcelableArrayList(LIST_STATE);
            adapter.setData(mListState);
        }

        setOnClick();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(LIST_STATE, mListState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        loading.show();
        return new MyAsyncTaskLoaderNowPlayingMovie(getActivity(), language);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        loading.dismiss();
        adapter.setData(data);
        mListState = data;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        loading.dismiss();
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
