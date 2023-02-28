package com.example.movielibrary.Utils.ImdbApi;

import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Models.SearchModels.TopListSearchResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("en/API/Top250Movies/key")
    Call<TopListSearchResult> getTop250Movies ();

    @GET("en/API/Top250TVs/key")
    Call<TopListSearchResult> getTopTvs ();

    @GET("en/API/MostPopularMovies/key")
    Call<TopListSearchResult> mostPopularMovies ();

    @GET("en/API/MostPopularTVs/key")
    Call<TopListSearchResult> mostPopularTvs ();

    @GET("en/API/InTheaters/key")
    Call<TopListSearchResult> inTheaters ();

    @GET("en/API/ComingSoon/key")
    Call<TopListSearchResult> comingSoon ();

    @GET("en/API/BoxOffice/key")
    Call<TopListSearchResult> boxOffice ();

    @GET("en/API/BoxOfficeAllTime/key")
    Call<TopListSearchResult> boxOfficeAllTime ();
}
