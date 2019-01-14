package com.example.katalogfilm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.katalogfilm.R;
import com.example.katalogfilm.data.model.MovieItems;
import com.example.katalogfilm.util.ViewOnItemClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<MovieItems> data;
    private Context context;
    private ViewOnItemClick.MovieItemCallback callback;

    public MovieAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
        notifyDataSetChanged();
    }

    public void setData(List<MovieItems> dataList) {
        data.clear();
        data.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setCallback(ViewOnItemClick.MovieItemCallback callback) {
        this.callback = callback;
    }

    public MovieItems getItem(int position) {
        return data.get(position);
    }

    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_items, viewGroup, false);
        final MovieHolder holder = new MovieHolder(view);
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
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        MovieItems m = data.get(i);
        movieHolder.tvTitle.setText(m.getTitle());
        movieHolder.tvDesc.setText(m.getOverview());
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + m.getPosterPath())
                .into(movieHolder.imgCover);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MovieHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDesc;
        ImageView imgCover;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_item_photo);
            tvTitle = itemView.findViewById(R.id.tv_item_name);
            tvDesc = itemView.findViewById(R.id.tv_item_desc);
        }
    }

}

