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
import com.example.movielibrary.Adapters.MovieDetails.TopLists.BoxOfficeRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.TopLists.BoxOfficeModel;
import com.example.movielibrary.Models.SearchModels.TopLists.TopListMovieResponseModel;
import com.example.movielibrary.MovieActivities.DetailsActivity;
import com.example.movielibrary.MovieActivities.MainActivity;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;

import java.util.Objects;

public class BoxOffice extends AppCompatActivity implements OnMovieResponseListener<TopListMovieResponseModel<BoxOfficeModel>>, OnMovieClickListener {

    RecyclerView recyclerView;
    BoxOfficeRecycleAdapter adapter;
    ConstraintLayout ConstrainLayout_LoadingAnimation;
    LottieAnimationView LottieAnimationView_AnimationLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LottieAnimationView_AnimationLoadingView = findViewById(R.id.LottieAnimationView_AnimationLoadingView);
        ConstrainLayout_LoadingAnimation = findViewById(R.id.ConstrainLayout_LoadingAnimation);

        recyclerView = findViewById(R.id.RecycleView_TopList);

        RequestManager requestManager = new RequestManager(this);

        requestManager.boxOffice(this);

    }

    @Override
    public void onResponse(TopListMovieResponseModel<BoxOfficeModel> result) {
        if(!result.getErrorMessage().equals("")){
            Toast.makeText(BoxOffice.this, result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(BoxOffice.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            handleResult(result);
        }
    }

    public void handleResult(TopListMovieResponseModel<BoxOfficeModel> result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(BoxOffice.this, 1));

        adapter = new BoxOfficeRecycleAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        LottieAnimationView_AnimationLoadingView.setVisibility(View.GONE);
        ConstrainLayout_LoadingAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(BoxOffice.this, error, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(BoxOffice.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(BoxOffice.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, BoxOffice.class.toString()));
    }
}