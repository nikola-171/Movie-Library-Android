package com.example.movielibrary.Models.ReviewsModel;

import java.util.List;

public class ReviewsResponseModel {
    private String imDbId;
    private String title;
    private String fullTitle;
    private String type;
    private String year;
    private String errorMessage;
    private List<ReviewItemModel> items;

    public String getImDbId() {
        return imDbId;
    }

    public void setImDbId(String imDbId) {
        this.imDbId = imDbId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ReviewItemModel> getItems() {
        return items;
    }

    public void setItems(List<ReviewItemModel> items) {
        this.items = items;
    }
}
