package com.example.movielibrary.MovieActivities.TopLists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.movielibrary.Adapters.MovieDetails.TopLists.ComingSoonRecycleAdapter;
import com.example.movielibrary.Adapters.MovieDetails.TopLists.InTheatersRecycleAdapter;
import com.example.movielibrary.Listeners.InTheatersListener;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Models.SearchModels.ComingSoonResponseModel;
import com.example.movielibrary.Models.SearchModels.InTheatersResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;

import java.util.Objects;

public class InTheaters extends AppCompatActivity implements InTheatersListener, OnMovieClickListener {

    RecyclerView recyclerView;
    InTheatersRecycleAdapter adapter;
    ConstraintLayout ConstrainLayout_LoadingAnimation;
    LottieAnimationView LottieAnimationView_AnimationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_theaters);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LottieAnimationView_AnimationLoadingView = findViewById(R.id.LottieAnimationView_AnimationLoadingView);
        ConstrainLayout_LoadingAnimation = findViewById(R.id.ConstrainLayout_LoadingAnimation);

        recyclerView = findViewById(R.id.RecycleView_TopList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.inTheaters(this);

    }

    @Override
    public void onResponse(InTheatersResponseModel result) {
        if (!result.getErrorMessage().equals("")) {
            Toast.makeText(InTheaters.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(InTheaters.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            handleResult(result);
        }
    }

    public void handleResult(InTheatersResponseModel result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(InTheaters.this, 1));

        adapter = new InTheatersRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        LottieAnimationView_AnimationLoadingView.setVisibility(View.GONE);
        ConstrainLayout_LoadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(InTheaters.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(InTheaters.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(InTheaters.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, InTheaters.class.toString()));
    }
}