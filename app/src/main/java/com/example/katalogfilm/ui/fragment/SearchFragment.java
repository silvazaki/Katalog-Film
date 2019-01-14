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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.MovieAdapter;
import com.example.katalogfilm.data.loopj.MyAsyncTaskLoaderSearchMovie;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.ui.activity.DetailMovieActivity;
import com.example.katalogfilm.util.ViewOnItemClick;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    MovieAdapter adapter;
    EditText editTextPencarian;
    Button buttonCari;
    RecyclerView listMovie;
    private String TAG = "hasil";
    private View view;
    private String language;


    public SearchFragment() {
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
        view = inflater.inflate(R.layout.fragment_search, container, false);

        editTextPencarian = view.findViewById(R.id.ed_cari);
        buttonCari = view.findViewById(R.id.btn_cari);
        listMovie = view.findViewById(R.id.listView);

        adapter = new MovieAdapter(getContext());
        listMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        listMovie.setAdapter(adapter);

        language = getResources().getString(R.string.bahasa);

        String query = "venom";
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, query);
        getLoaderManager().initLoader(0, bundle, this);

        setOnClick();

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, @Nullable Bundle args) {
        String query = "";
        if (args != null) {
            query = args.getString(EXTRAS_FILM);
        }

        return new MyAsyncTaskLoaderSearchMovie(getActivity(), query, language);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }


    public void setOnClick() {


        buttonCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String query = editTextPencarian.getText().toString();

                if (TextUtils.isEmpty(query)) return;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM, query);
                getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
            }
        });

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
