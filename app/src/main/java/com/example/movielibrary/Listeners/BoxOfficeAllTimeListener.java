package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.BoxOfficeAllTimeResponseModel;

public interface BoxOfficeAllTimeListener {
    void onResponse(BoxOfficeAllTimeResponseModel result);
    void onError(String error);
}
