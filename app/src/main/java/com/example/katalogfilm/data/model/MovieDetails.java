package com.example.katalogfilm.data.model;

import java.util.List;

/**
 * Created by User on 1/13/2019.
 */

public class MovieDetails {
    private List<Genre> genres;
    private String tagline;

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
