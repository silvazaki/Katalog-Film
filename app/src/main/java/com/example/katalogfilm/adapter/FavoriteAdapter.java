package com.example.katalogfilm.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.util.ViewOnItemClick;
import com.squareup.picasso.Picasso;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewholder> {
    private Cursor listNotes;
    private Context context;
    private ViewOnItemClick.MovieItemCallback callback;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setListNotes(Cursor listNotes) {
        this.listNotes = listNotes;
        notifyDataSetChanged();
    }

    public void setCallback(ViewOnItemClick.MovieItemCallback callback) {
        this.callback = callback;
    }

    @Override
    public FavoriteViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_items, viewGroup, false);
        final FavoriteAdapter.FavoriteViewholder holder = new FavoriteAdapter.FavoriteViewholder(view);
        if (callback != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(holder.getPosition(), view);
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewholder holder, int position) {
        final MovieItems items = getItem(position);
        holder.tvTitle.setText(items.getTitle());
        holder.tvDescription.setText(items.getOverview());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + items.getPosterPath())
                .into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        if (listNotes == null) return 0;
        return listNotes.getCount();
    }

    public MovieItems getItem(int position) {
        if (!listNotes.moveToPosition(position)) {
        }
        return new MovieItems(listNotes);
    }

    class FavoriteViewholder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        ImageView imgCover;

        FavoriteViewholder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_desc);
            imgCover = itemView.findViewById(R.id.img_item_photo);
        }
    }
}