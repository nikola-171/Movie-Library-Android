package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.SearchResult;

public interface OnSearchMoviesListener {
    void onResponse(SearchResult result);
    void onError(String error);
}
