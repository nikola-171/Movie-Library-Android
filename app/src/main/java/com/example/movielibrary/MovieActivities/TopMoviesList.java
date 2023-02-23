package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnTopListMovieSearchListener;
import com.example.movielibrary.Models.SearchModels.TopListSearchResult;
import com.example.movielibrary.R;
import com.example.movielibrary.Adapters.MovieDetails.TopMoviesListRecycleAdapter;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;

import java.util.Objects;

public class TopMoviesList extends AppCompatActivity implements OnTopListMovieSearchListener, OnMovieClickListener {

    RecyclerView recyclerView;
    TopMoviesListRecycleAdapter adapter;
    ConstraintLayout ConstrainLayout_LoadingAnimation;
    LottieAnimationView LottieAnimationView_AnimationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LottieAnimationView_AnimationLoadingView = findViewById(R.id.LottieAnimationView_AnimationLoadingView);
        ConstrainLayout_LoadingAnimation = findViewById(R.id.ConstrainLayout_LoadingAnimation);
        RequestManager requestManager = new RequestManager(TopMoviesList.this);

        recyclerView = findViewById(R.id.RecycleView_TopList);

        requestManager.top250MoviesSearch(this);

    }

    private void handleTop250MoviesList(TopListSearchResult result){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(TopMoviesList.this, 1));

        adapter = new TopMoviesListRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        LottieAnimationView_AnimationLoadingView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(TopListSearchResult result) {
        if(!result.getErrorMessage().equals("")){
            Toast.makeText(TopMoviesList.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(TopMoviesList.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            handleTop250MoviesList(result);
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(TopMoviesList.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(TopMoviesList.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
            startActivity(new Intent(TopMoviesList.this, DetailsActivity.class)
                    .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, TopMoviesList.class.toString()));

    }
}