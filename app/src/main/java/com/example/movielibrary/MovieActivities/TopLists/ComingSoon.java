package com.example.movielibrary.MovieActivities.TopLists;

import android.content.Context;

import com.example.movielibrary.Utils.ImdbApi.RequestManager;

public class ComingSoon extends TopLists {

    @Override
    protected void getDataList(Context context){
        RequestManager requestManager = new RequestManager(context);

        requestManager.comingSoon(this);
    }
}