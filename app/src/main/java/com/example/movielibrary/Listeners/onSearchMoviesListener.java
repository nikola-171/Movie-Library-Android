package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.SearchResult;

public interface onSearchMoviesListener {
    void onResponse(SearchResult result);
    void onError(String error);
}
