package com.example.movielibrary.Models.Database;

public class MovieDetailsEntity {
    String title;
    String poster;
    String movieId;
    String id;

    public MovieDetailsEntity(String id, String title, String poster, String movieId) {
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
