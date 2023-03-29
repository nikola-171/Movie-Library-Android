package com.example.movielibrary.Utils;

import static com.example.movielibrary.Shared.Settings.IMDB_API_KEY;


import android.content.Context;

import com.example.movielibrary.Database.DBHandler;

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
}
