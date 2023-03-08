package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class InTheatersResponseModel {
    List<InTheatersModel> items;
    String errorMessage;

    public List<InTheatersModel> getItems() {
        return items;
    }

    public void setItems(List<InTheatersModel> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public InTheatersResponseModel(List<InTheatersModel> items, String errorMessage) {
        this.items = items;
        this.errorMessage = errorMessage;
    }
}
