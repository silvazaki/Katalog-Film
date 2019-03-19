package com.example.katalogfilm.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.katalogfilm.R;
import com.example.katalogfilm.data.model.MovieItems;

import java.util.concurrent.ExecutionException;

import static com.example.katalogfilm.data.database.DatabaseContract.CONTENT_URI;

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Cursor cursor;
    private int appWidgetId;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        if (cursor == null)
            return 0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        MovieItems item = getItem(position);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w185" + item.getPosterPath())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(ImagesFavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    private MovieItems getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new MovieItems(cursor);

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}