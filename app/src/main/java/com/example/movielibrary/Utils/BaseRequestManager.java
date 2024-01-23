package com.example.movielibrary.Utils;

import static com.example.movielibrary.Shared.Settings.IMDB_API_KEY;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRequestManager {
    protected final String imdbApiKey;

    protected final String baseUrl;

    protected final Context context;

    protected final Retrofit retrofit;

    public BaseRequestManager(Context context) {
        this.context = context;
        imdbApiKey = new DBHandler(context).getSettingByName(IMDB_API_KEY);
        baseUrl = "https://imdb-api.com/";

        retrofit = new Retrofit.Builder()
                .baseUrl("https://imdb-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> void setCallbackFunction(Call<T> call, OnMovieResponseListener<T> listener){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, R.string.requestManager_requestFailed, Toast.LENGTH_LONG).show();
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
