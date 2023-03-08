package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class ComingSoonResponseModel {
    List<ComingSoonModel> items;
    String errorMessage;

    public List<ComingSoonModel> getItems() {
        return items;
    }

    public void setItems(List<ComingSoonModel> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ComingSoonResponseModel(List<ComingSoonModel> items, String errorMessage) {
        this.items = items;
        this.errorMessage = errorMessage;
    }
}
