package com.example.movielibrary.Models.SearchModels;

public class SimilarMovieModel {

    String id;
    String title;
    String image;
    String imDbRating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public SimilarMovieModel(String id, String title, String image, String imDbRating) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.imDbRating = imDbRating;
    }
}
