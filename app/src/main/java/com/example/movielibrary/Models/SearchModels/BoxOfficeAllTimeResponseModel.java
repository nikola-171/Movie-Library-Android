package com.example.movielibrary.Models.SearchModels;

import java.util.List;

public class BoxOfficeAllTimeResponseModel {

    List<BoxOfficeAllTimeModel> items;
    String errorMessage;

    public List<BoxOfficeAllTimeModel> getItems() {
        return items;
    }

    public void setItems(List<BoxOfficeAllTimeModel> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BoxOfficeAllTimeResponseModel(List<BoxOfficeAllTimeModel> items, String errorMessage) {
        this.items = items;
        this.errorMessage = errorMessage;
    }
}
