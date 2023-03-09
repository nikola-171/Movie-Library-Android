package com.example.movielibrary.Utils.ImdbApi;

import static com.example.movielibrary.Shared.Settings.IMDB_API_KEY;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Listeners.onMovieDetailsSearchListener;
import com.example.movielibrary.Listeners.onSearchMoviesListener;
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
import com.example.movielibrary.Models.SearchModels.TopListMovieResponseModel;
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

    public void top250MoviesSearch(OnMovieResponseListener<TopListMovieResponseModel<Top250MoviesModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<Top250MoviesModel>> call = searchMovies.getTop250Movies(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void top250TvsSearch(OnMovieResponseListener<TopListMovieResponseModel<Top250TvsModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<Top250TvsModel>> call = searchMovies.getTopTvs(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void mostPopularMovies(OnMovieResponseListener<TopListMovieResponseModel<MostPopularMoviesModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<MostPopularMoviesModel>> call = searchMovies.mostPopularMovies(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void mostPopularTvs(OnMovieResponseListener<TopListMovieResponseModel<MostPopularTvsModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<MostPopularTvsModel>> call = searchMovies.mostPopularTvs(imdbApiKey);

        setCallbackFunction(call, listener);
    }


    public void inTheaters(OnMovieResponseListener<TopListMovieResponseModel<InTheatersModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<InTheatersModel>> call = searchMovies.inTheaters(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void comingSoon(OnMovieResponseListener<TopListMovieResponseModel<ComingSoonModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<ComingSoonModel>> call = searchMovies.comingSoon(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void boxOffice(OnMovieResponseListener<TopListMovieResponseModel<BoxOfficeModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<BoxOfficeModel>> call = searchMovies.boxOffice(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    public void boxOfficeAllTime(OnMovieResponseListener<TopListMovieResponseModel<BoxOfficeAllTimeModel>> listener){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<TopListMovieResponseModel<BoxOfficeAllTimeModel>> call = searchMovies.boxOfficeAllTime(imdbApiKey);

        setCallbackFunction(call, listener);
    }

    private <T> void setCallbackFunction(Call<T> call, OnMovieResponseListener<T> listener){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.RequestManager_RequestFailed, Toast.LENGTH_LONG).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
