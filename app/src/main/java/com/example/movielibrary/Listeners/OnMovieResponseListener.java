package com.example.movielibrary.Listeners;

import com.example.movielibrary.Models.SearchModels.SearchResult;

public interface OnMovieResponseListener<T> {
    void onResponse(T result);
    void onError(String error);
}
