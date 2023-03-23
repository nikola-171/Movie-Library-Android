package com.example.movielibrary.Models.SearchModels;

import java.util.Comparator;

public class MovieSearchResult {

    String id;
    String resultType;
    String image;
    String title;
    String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Comparator<MovieSearchResult> comparatorTitleAsc = Comparator.comparing(MovieSearchResult::getTitle);
    public static Comparator<MovieSearchResult> comparatorTitleDesc = (t1, t2) -> t2.getTitle().compareTo(t1.getTitle());

}
