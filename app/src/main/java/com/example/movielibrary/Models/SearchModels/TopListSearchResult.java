package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class TopListSearchResult {

    List<TopListMovieModel> items;
    String errorMessage;

    public List<TopListMovieModel> getItems() {
        return items;
    }

    public void setItems(List<TopListMovieModel> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
