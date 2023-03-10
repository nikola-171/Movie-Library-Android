package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;

public interface OnMovieDetailsSearchListener {
    void onResponse(DetailsMovieResponse response);
    void onError(String message);
}


