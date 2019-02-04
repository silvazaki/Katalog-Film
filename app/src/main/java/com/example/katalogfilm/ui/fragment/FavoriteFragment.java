package com.example.katalogfilm.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.katalogfilm.R;
import com.example.katalogfilm.adapter.FavoriteAdapter;
import com.example.katalogfilm.ui.activity.DetailMovieActivity;
import com.example.katalogfilm.util.ViewOnItemClick;

import static com.example.katalogfilm.data.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private Cursor list;
    private FavoriteAdapter adapter;
    private RecyclerView rvFav;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvFav = view.findViewById(R.id.rv_favorit);
        rvFav.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFav.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getContext());
        adapter.setListNotes(list);
        rvFav.setAdapter(adapter);

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

        new LoadMovieAsync().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            list = cursor;
            adapter.setListNotes(list);
        }
    }
}
