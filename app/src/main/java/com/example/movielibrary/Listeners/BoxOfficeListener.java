package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.BoxOfficeAllTimeResponseModel;
import com.example.movielibrary.Models.SearchModels.BoxOfficeResponseModel;

public interface BoxOfficeListener {
    void onResponse(BoxOfficeResponseModel result);
    void onError(String error);
}
