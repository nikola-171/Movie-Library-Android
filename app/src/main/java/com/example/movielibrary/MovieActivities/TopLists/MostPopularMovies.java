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
import com.example.movielibrary.Adapters.MovieDetails.TopLists.MostPopularMoviesRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.TopLists.MostPopularMoviesModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;

import java.util.Objects;

public class MostPopularMovies extends AppCompatActivity implements OnMovieResponseListener<TopListMovieResponseModel<MostPopularMoviesModel>>, OnMovieClickListener {

    RecyclerView recyclerView;
    MostPopularMoviesRecycleAdapter adapter;
    ConstraintLayout constrainLayout_loadingAnimation;
    LottieAnimationView lottieAnimationView_animationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular_movies);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lottieAnimationView_animationLoadingView = findViewById(R.id.lottieAnimationView_animationLoadingView);
        constrainLayout_loadingAnimation = findViewById(R.id.constrainLayout_loadingAnimation);

        recyclerView = findViewById(R.id.recycleView_topList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.mostPopularMovies(this);

    }

    @Override
    public void onResponse(TopListMovieResponseModel<MostPopularMoviesModel> result) {
        if (!result.getErrorMessage().equals("")) {
            Toast.makeText(MostPopularMovies.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MostPopularMovies.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            handleResult(result);
        }
    }

    public void handleResult(TopListMovieResponseModel<MostPopularMoviesModel> result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MostPopularMovies.this, 1));

        adapter = new MostPopularMoviesRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        constrainLayout_loadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(MostPopularMovies.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MostPopularMovies.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MostPopularMovies.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, MostPopularMovies.class.toString()));
    }
}