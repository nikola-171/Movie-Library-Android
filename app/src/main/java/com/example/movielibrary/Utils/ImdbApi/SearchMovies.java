package com.example.movielibrary.Utils.ImdbApi;

import com.example.movielibrary.Models.SearchModels.BoxOfficeAllTimeResponseModel;
import com.example.movielibrary.Models.SearchModels.BoxOfficeResponseModel;
import com.example.movielibrary.Models.SearchModels.ComingSoonResponseModel;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.InTheatersResponseModel;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Models.SearchModels.TopListSearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SearchMovies {

    @GET("en/API/Search/{api_key}/{movie_title}")
    Call<SearchResult> searchMovies(
            @Path("movie_title") String movie_title,
            @Path("api_key") String api_key
    );

    @GET("en/API/Title/{api_key}/{movie_id}")
    Call<DetailsMovieResponse> getMovieDetails (
            @Path("movie_id") String movie_id,
            @Path("api_key") String api_key
    );

    @GET("en/API/AdvancedSearch/{api_key}")
    Call<SearchResult> advancedSearch(
            @Path("api_key") String api_key,
            @QueryMap Map<String, Object> map
    );

    @GET("en/API/Top250Movies/{api_key}")
    Call<TopListSearchResult> getTop250Movies (
            @Path("api_key") String api_key
    );

    @GET("en/API/Top250TVs/{api_key}")
    Call<TopListSearchResult> getTopTvs (
            @Path("api_key") String api_key
    );

    @GET("en/API/MostPopularMovies/{api_key}")
    Call<TopListSearchResult> mostPopularMovies (
            @Path("api_key") String api_key
    );

    @GET("en/API/MostPopularTVs/{api_key}")
    Call<TopListSearchResult> mostPopularTvs (
            @Path("api_key") String api_key
    );

    @GET("en/API/InTheaters/{api_key}")
    Call<InTheatersResponseModel> inTheaters (
            @Path("api_key") String api_key
    );

    @GET("en/API/ComingSoon/{api_key}")
    Call<ComingSoonResponseModel> comingSoon (
            @Path("api_key") String api_key
    );

    @GET("en/API/BoxOffice/{api_key}")
    Call<BoxOfficeResponseModel> boxOffice (
            @Path("api_key") String api_key
    );

    @GET("en/API/BoxOfficeAllTime/{api_key}")
    Call<BoxOfficeAllTimeResponseModel> boxOfficeAllTime (
            @Path("api_key") String api_key
    );
}
