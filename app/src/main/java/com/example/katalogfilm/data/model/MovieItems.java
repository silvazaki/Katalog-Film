package com.example.katalogfilm.data.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.katalogfilm.data.database.DatabaseContract;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.example.katalogfilm.data.database.DatabaseContract.MovieColumns.ID_FILM;
import static com.example.katalogfilm.data.database.DatabaseContract.getColumnInt;
import static com.example.katalogfilm.data.database.DatabaseContract.getColumnString;

/**
 * Created by User on 1/12/2019.
 */

public class MovieItems implements Parcelable {
    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    private int id;
    private boolean video;
    @SerializedName("vote_average")
    @Expose
    private String voteAverage;
    private String title;
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    private boolean adult;
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public MovieItems() {
    }

    public MovieItems(Cursor cursor) {
        this.id = getColumnInt(cursor, ID_FILM);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.MovieColumns.DESCRIPTION);
        this.posterPath = getColumnString(cursor, DatabaseContract.MovieColumns.IMAGE);
        this.popularity = getColumnInt(cursor, DatabaseContract.MovieColumns.POPULARITY);
        this.voteAverage = getColumnString(cursor, DatabaseContract.MovieColumns.RATING);
        this.originalLanguage = getColumnString(cursor, DatabaseContract.MovieColumns.LANGUAGE);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE);
    }

    public MovieItems(Parcel in) {
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readString();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.backdropPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.voteCount);
        dest.writeInt(this.id);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeString(this.voteAverage);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }
}
