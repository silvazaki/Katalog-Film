package com.example.katalogfilm.data.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.katalogfilm.data.model.MovieItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by User on 1/12/2019.
 */

public class MyAsyncTaskLoaderUpcomingMovie extends AsyncTaskLoader<ArrayList<MovieItems>> {

    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String TAG = "hasil";
    private String language;

    public MyAsyncTaskLoaderUpcomingMovie(Context context, String language) {
        super(context);

        onContentChanged();
        this.language = language;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    protected void onReleaseResources(ArrayList<MovieItems> data) {
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=3dce2cc3191c483d42c878e6409fd560" +
                "&language=" + language + "";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Gson gson = new GsonBuilder().create();
                try {
                    JSONObject obj = new JSONObject(new String(responseBody));
                    JSONArray results = obj.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        movieItemses.add(gson.fromJson(results.getString(i), MovieItems.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onSuccess: " + e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailure: " + error.getMessage());
            }

        });

        return movieItemses;
    }
}
