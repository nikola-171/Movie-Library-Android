package com.example.movielibrary.Utils.ImdbApi;

import static com.example.movielibrary.Shared.Settings.IMDB_API_KEY;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Listeners.BoxOfficeAllTimeListener;
import com.example.movielibrary.Listeners.onMovieDetailsSearchListener;
import com.example.movielibrary.Listeners.onSearchMoviesListener;
import com.example.movielibrary.Listeners.OnTopListMovieSearchListener;
import com.example.movielibrary.Models.SearchModels.BoxOfficeAllTimeResponseModel;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Models.SearchModels.TopListSearchResult;
import com.example.movielibrary.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
        imdbApiKey = new DBHandler(context).getSettingByName(IMDB_API_KEY);
    }

    private String imdbApiKey;

    public void searchMovies(onSearchMoviesListener listener, String movie_title){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<SearchResult> call = searchMovies.searchMovies(movie_title, imdbApiKey);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void advancedSearchMovies(onSearchMoviesListener listener, HashMap<String, Object> data){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<SearchResult> call = searchMovies.advancedSearch(imdbApiKey, data);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void searchMovieDetails(onMovieDetailsSearchListener listener, String movie_id){
        SearchMovies getMovieDetails = retrofit.create(SearchMovies.class);
        Call<DetailsMovieResponse> call = getMovieDetails.getMovieDetails(movie_id, imdbApiKey);

        call.enqueue(new Callback<DetailsMovieResponse>() {
            @Override
            public void onResponse(Call<DetailsMovieResponse> call, Response<DetailsMovieResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailsMovieResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void top250MoviesSearch(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.getTop250Movies(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(Call<TopListSearchResult> call, Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TopListSearchResult> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void topTvsSearch(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.getTopTvs(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void mostPopularMovies(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.mostPopularMovies(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void mostPopularTvs(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.mostPopularTvs(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void inTheaters(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.inTheaters(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void comingSoon(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.comingSoon(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void boxOffice(OnTopListMovieSearchListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListSearchResult> call = searchMovies.boxOffice(imdbApiKey);

        call.enqueue(new Callback<TopListSearchResult>() {
            @Override
            public void onResponse(@NonNull Call<TopListSearchResult> call, @NonNull Response<TopListSearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TopListSearchResult> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void boxOfficeAllTime(BoxOfficeAllTimeListener listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<BoxOfficeAllTimeResponseModel> call = searchMovies.boxOfficeAllTime(imdbApiKey);

        call.enqueue(new Callback<BoxOfficeAllTimeResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<BoxOfficeAllTimeResponseModel> call, @NonNull Response<BoxOfficeAllTimeResponseModel> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BoxOfficeAllTimeResponseModel> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
