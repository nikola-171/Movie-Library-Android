package com.example.movielibrary.Utils.ImdbApi;

import com.example.movielibrary.Models.FaqModels.FaqResponseModel;
import com.example.movielibrary.Models.Images.ImagesResponseModel;
import com.example.movielibrary.Models.ReviewsModel.ReviewsResponseModel;
import com.example.movielibrary.Models.SearchModels.TopLists.BoxOfficeAllTimeModel;
import com.example.movielibrary.Models.SearchModels.TopLists.BoxOfficeModel;
import com.example.movielibrary.Models.SearchModels.TopLists.ComingSoonModel;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.TopLists.InTheatersModel;
import com.example.movielibrary.Models.SearchModels.TopLists.MostPopularMoviesModel;
import com.example.movielibrary.Models.SearchModels.TopLists.MostPopularTvsModel;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Models.SearchModels.TopLists.Top250MoviesModel;
import com.example.movielibrary.Models.SearchModels.TopLists.Top250TvsModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SearchMovies {

    @GET("en/API/Images/{api_key}/{id}/Full")
    Call<ImagesResponseModel> getImages(
            @Path("api_key") String api_key,
            @Path("id") String id
    );

    @GET("en/API/Search/{api_key}/{title}")
    Call<SearchResult> defaultSearch(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchMovie/{api_key}/{title}")
    Call<SearchResult> searchMovies(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchSeries/{api_key}/{title}")
    Call<SearchResult> searchSeries(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchCompany/{api_key}/{title}")
    Call<SearchResult> searchCompany(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchKeyword/{api_key}/{title}")
    Call<SearchResult> searchKeywords(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchEpisode/{api_key}/{title}")
    Call<SearchResult> searchEpisodes(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchAll/{api_key}/{title}")
    Call<SearchResult> searchAll(
            @Path("title") String title,
            @Path("api_key") String api_key
    );

    @GET("en/API/SearchName/{api_key}/{title}")
    Call<SearchResult> searchNames(
            @Path("title") String title,
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
    Call<TopListMovieResponseModel<Top250MoviesModel>> getTop250Movies (
            @Path("api_key") String api_key
    );

    @GET("en/API/Top250TVs/{api_key}")
    Call<TopListMovieResponseModel<Top250TvsModel>> getTopTvs (
            @Path("api_key") String api_key
    );

    @GET("en/API/MostPopularMovies/{api_key}")
    Call<TopListMovieResponseModel<MostPopularMoviesModel>> mostPopularMovies (
            @Path("api_key") String api_key
    );

    @GET("en/API/MostPopularTVs/{api_key}")
    Call<TopListMovieResponseModel<MostPopularTvsModel>> mostPopularTvs (
            @Path("api_key") String api_key
    );

    @GET("en/API/InTheaters/{api_key}")
    Call<TopListMovieResponseModel<InTheatersModel>> inTheaters (
            @Path("api_key") String api_key
    );

    @GET("en/API/ComingSoon/{api_key}")
    Call<TopListMovieResponseModel<ComingSoonModel>> comingSoon (
            @Path("api_key") String api_key
    );

    @GET("en/API/BoxOffice/{api_key}")
    Call<TopListMovieResponseModel<BoxOfficeModel>> boxOffice (
            @Path("api_key") String api_key
    );

    @GET("en/API/BoxOfficeAllTime/{api_key}")
    Call<TopListMovieResponseModel<BoxOfficeAllTimeModel>> boxOfficeAllTime (
            @Path("api_key") String api_key
    );

    @GET("en/API/FAQ/{api_key}/{item_id}")
    Call<FaqResponseModel> getFaqList (
            @Path("api_key") String api_key,
            @Path("item_id") String item_id
    );

    @GET("en/API/Reviews/{api_key}/{item_id}")
    Call<ReviewsResponseModel> getReviewsList (
            @Path("api_key") String api_key,
            @Path("item_id") String item_id
    );
}
