package com.example.movielibrary.Database.Entities;

public class MovieDetails {
    String title;
    String poster;
    String movieId;
    String id;

    public MovieDetails(String id, String title, String poster, String movieId) {
        this.title = title;
        this.poster = poster;
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getId() {
        return id;
    }
}
