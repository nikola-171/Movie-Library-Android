package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.TopListSearchResult;

public interface OnTopListMovieSearchListener {
    void onResponse(TopListSearchResult result);
    void onError(String error);
}
