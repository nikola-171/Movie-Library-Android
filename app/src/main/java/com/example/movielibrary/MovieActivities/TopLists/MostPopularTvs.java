package com.example.movielibrary.MovieActivities.TopLists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.movielibrary.Adapters.MovieDetails.TopLists.MostPopularTvsRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.TopLists.MostPopularTvsModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;

import java.util.Objects;

public class MostPopularTvs extends AppCompatActivity implements OnMovieResponseListener<TopListMovieResponseModel<MostPopularTvsModel>>, OnMovieClickListener {

    RecyclerView recyclerView;
    MostPopularTvsRecycleAdapter adapter;
    ConstraintLayout ConstrainLayout_LoadingAnimation;
    LottieAnimationView LottieAnimationView_AnimationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular_tvs);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LottieAnimationView_AnimationLoadingView = findViewById(R.id.LottieAnimationView_AnimationLoadingView);
        ConstrainLayout_LoadingAnimation = findViewById(R.id.ConstrainLayout_LoadingAnimation);

        recyclerView = findViewById(R.id.RecycleView_TopList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.mostPopularTvs(this);

    }

    @Override
    public void onResponse(TopListMovieResponseModel<MostPopularTvsModel> result) {
        if (!result.getErrorMessage().equals("")) {
            Toast.makeText(MostPopularTvs.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MostPopularTvs.this, MainActivity.class);
            startActivity(i);
            finish();
        } else {
            handleResult(result);
        }
    }

    public void handleResult(TopListMovieResponseModel<MostPopularTvsModel> result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MostPopularTvs.this, 1));

        adapter = new MostPopularTvsRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        LottieAnimationView_AnimationLoadingView.setVisibility(View.GONE);
        ConstrainLayout_LoadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(MostPopularTvs.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MostPopularTvs.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MostPopularTvs.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, MostPopularTvs.class.toString()));
    }
}