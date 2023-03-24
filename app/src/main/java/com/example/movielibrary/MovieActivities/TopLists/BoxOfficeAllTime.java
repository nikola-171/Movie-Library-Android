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
import com.example.movielibrary.Adapters.TopLists.BoxOfficeAllTimeRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.TopLists.BoxOfficeAllTimeModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;

import java.util.Objects;

public class BoxOfficeAllTime extends AppCompatActivity implements OnMovieResponseListener<TopListMovieResponseModel<BoxOfficeAllTimeModel>>, OnMovieClickListener {

    RecyclerView recyclerView;
    BoxOfficeAllTimeRecycleAdapter adapter;
    ConstraintLayout constrainLayout_loadingAnimation;
    LottieAnimationView lottieAnimationView_animationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_all_time);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        lottieAnimationView_animationLoadingView = findViewById(R.id.lottieAnimationView_animationLoadingView);
        constrainLayout_loadingAnimation = findViewById(R.id.constrainLayout_loadingAnimation);

        recyclerView = findViewById(R.id.recycleView_topList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.boxOfficeAllTime(this);

    }

    @Override
    public void onResponse(TopListMovieResponseModel<BoxOfficeAllTimeModel> result) {
        if(!result.getErrorMessage().equals("")){
            Toast.makeText(BoxOfficeAllTime.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BoxOfficeAllTime.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            handleResult(result);
        }
    }

    public void handleResult(TopListMovieResponseModel<BoxOfficeAllTimeModel> result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(BoxOfficeAllTime.this, 1));

        adapter = new BoxOfficeAllTimeRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        lottieAnimationView_animationLoadingView.setVisibility(View.GONE);
        constrainLayout_loadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(BoxOfficeAllTime.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(BoxOfficeAllTime.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(BoxOfficeAllTime.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, BoxOfficeAllTime.class.toString()));
    }
}