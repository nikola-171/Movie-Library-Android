package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.BoxOfficeResponseModel;
import com.example.movielibrary.Models.SearchModels.ComingSoonResponseModel;

public interface ComingSoonListener {
    void onResponse(ComingSoonResponseModel result);
    void onError(String error);
}
