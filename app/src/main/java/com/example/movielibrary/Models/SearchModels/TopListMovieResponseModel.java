package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class TopListMovieResponseModel<T> {

    List<T> items;
    String errorMessage;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
