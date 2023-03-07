package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class BoxOfficeResponseModel {
    List<BoxOfficeModel> items;
    String errorMessage;

    public List<BoxOfficeModel> getItems() {
        return items;
    }

    public void setItems(List<BoxOfficeModel> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BoxOfficeResponseModel(List<BoxOfficeModel> items, String errorMessage) {
        this.items = items;
        this.errorMessage = errorMessage;
    }
}
