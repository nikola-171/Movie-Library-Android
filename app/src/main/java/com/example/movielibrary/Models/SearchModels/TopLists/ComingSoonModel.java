package com.example.movielibrary.Models.SearchModels.TopLists;

public class ComingSoonModel {

    String id;
    String title;
    String fullTitle;
    String year;
    String releaseState;
    String image;
    String runtimeMins;
    String runtimeStr;
    String plot;
    String contentRating;
    String imDbRating;
    String imDbRatingCount;
    String metacriticRating;
    String genres;
    String directors;
    String stars;

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

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(String releaseState) {
        this.releaseState = releaseState;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRuntimeMins() {
        return runtimeMins;
    }

    public void setRuntimeMins(String runtimeMins) {
        this.runtimeMins = runtimeMins;
    }

    public String getRuntimeStr() {
        return runtimeStr;
    }

    public void setRuntimeStr(String runtimeStr) {
        this.runtimeStr = runtimeStr;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(String imDbRating) {
        this.imDbRating = imDbRating;
    }

    public String getImDbRatingCount() {
        return imDbRatingCount;
    }

    public void setImDbRatingCount(String imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public ComingSoonModel(String id, String title, String fullTitle, String year, String releaseState, String image, String runtimeMins, String runtimeStr, String plot, String contentRating, String imDbRating, String imDbRatingCount, String metacriticRating, String genres, String directors, String stars) {
        this.id = id;
        this.title = title;
        this.fullTitle = fullTitle;
        this.year = year;
        this.releaseState = releaseState;
        this.image = image;
        this.runtimeMins = runtimeMins;
        this.runtimeStr = runtimeStr;
        this.plot = plot;
        this.contentRating = contentRating;
        this.imDbRating = imDbRating;
        this.imDbRatingCount = imDbRatingCount;
        this.metacriticRating = metacriticRating;
        this.genres = genres;
        this.directors = directors;
        this.stars = stars;
    }
}
