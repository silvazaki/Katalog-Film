package com.example.katalogfilm.data.loopj;

import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
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

public class MyAsyncTaskLoaderSearchMovie extends AsyncTaskLoader<ArrayList<MovieItems>> {

    ProgressDialog dialog;
    private ArrayList<MovieItems> mData;
    private boolean mHasResult = false;
    private String query;
    private String TAG = "hasil";

    public MyAsyncTaskLoaderSearchMovie(Context context, String query) {
        super(context);

        onContentChanged();
        dialog = new ProgressDialog(context);
        this.query = query;

    }

    @Override
    protected void onStartLoading() {
        dialog.setMessage("loading");
        dialog.show();
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        dialog.dismiss();
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
        String url = "https://api.themoviedb.org/3/search/movie?api_key=3dce2cc3191c483d42c878e6409fd560&query=" + query;
        Log.e(TAG, "loadInBackground: " + url);

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
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
                        Log.e(TAG, "onSuccess: " + movieItemses.get(i).getTitle());
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onSuccess: " + e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailure: " + error.getMessage());
                dialog.dismiss();
            }

        });

        return movieItemses;
    }
}
