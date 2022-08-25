package com.example.movielibrary.Utils.Search;

import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetMovieDetails {

    @GET("en/API/Title/{apiKey}/{movie_id}")
    Call<DetailsMovieResponse> getMovieDetails (
            @Path("movie_id") String movie_id
    );
}
