package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.ComingSoonResponseModel;
import com.example.movielibrary.Models.SearchModels.InTheatersResponseModel;

public interface InTheatersListener {
    void onResponse(InTheatersResponseModel result);
    void onError(String error);
}
