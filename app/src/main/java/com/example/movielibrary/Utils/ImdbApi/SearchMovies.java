package com.example.movielibrary.Utils.ImdbApi;

import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.SearchResult;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface SearchMovies {

    @GET("en/API/Search/key/{movie_title}")
    Call<SearchResult> searchMovies(
            @Path("movie_title") String movie_title
    );

    @GET("en/API/Title/key/{movie_id}")
    Call<DetailsMovieResponse> getMovieDetails (
            @Path("movie_id") String movie_id
    );

    @GET("en/API/AdvancedSearch/key")
    Call<SearchResult> advancedSearch(
            @QueryMap Map<String, Object> map
    );

}
