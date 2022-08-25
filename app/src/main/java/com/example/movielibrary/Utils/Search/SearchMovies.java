package com.example.movielibrary.Utils.Search;

import com.example.movielibrary.Models.SearchModels.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchMovies {

    @GET("en/API/Search/{apiKey}/{movie_title}")
    Call<SearchResult> searchMovies(
            @Path("movie_title") String movie_title
    );

}
