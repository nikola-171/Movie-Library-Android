package com.example.movielibrary.Utils;

import android.content.Context;
import android.widget.Toast;

import com.example.movielibrary.Listeners.OnSearchMoviesListener;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Utils.Search.SearchMovies;

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
    }


    public void searchMovies(OnSearchMoviesListener listener, String movie_title){
        SearchMovies searchMovies = retrofit.create(SearchMovies.class);
        Call<SearchResult> call = searchMovies.searchMovies(movie_title);

        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Couldn't fetch the data", Toast.LENGTH_LONG).show();
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
}
