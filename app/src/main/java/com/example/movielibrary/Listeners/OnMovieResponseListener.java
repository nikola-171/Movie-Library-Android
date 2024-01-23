package com.example.movielibrary.Listeners;

public interface OnMovieResponseListener<T> {
    void onResponse(T result);
    void onError(String error);
}
