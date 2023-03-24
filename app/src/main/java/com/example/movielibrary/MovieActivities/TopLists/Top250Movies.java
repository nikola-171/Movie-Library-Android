package com.example.movielibrary.MovieActivities.TopLists;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.movielibrary.Adapters.TopLists.Top250MoviesRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.TopLists.Top250MoviesModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;

import java.util.Objects;

public class Top250Movies extends AppCompatActivity implements OnMovieResponseListener<TopListMovieResponseModel<Top250MoviesModel>>, OnMovieClickListener {

    RecyclerView recyclerView;
    Top250MoviesRecycleAdapter adapter;
    ConstraintLayout constrainLayout_loadingAnimation;
    LottieAnimationView lottieAnimationView_animationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_250_movies);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lottieAnimationView_animationLoadingView = findViewById(R.id.lottieAnimationView_animationLoadingView);
        constrainLayout_loadingAnimation = findViewById(R.id.constrainLayout_loadingAnimation);

        recyclerView = findViewById(R.id.recycleView_topList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.top250MoviesSearch(this);

    }

    @Override
    public void onResponse(TopListMovieResponseModel<Top250MoviesModel> result) {
        if (!result.getErrorMessage().equals("")) {
            Toast.makeText(Top250Movies.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Top250Movies.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            handleResult(result);
        }
    }

    public void handleResult(TopListMovieResponseModel<Top250MoviesModel> result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(Top250Movies.this, 1));

        adapter = new Top250MoviesRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        constrainLayout_loadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(Top250Movies.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Top250Movies.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(Top250Movies.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, Top250Movies.class.toString()));
    }
}