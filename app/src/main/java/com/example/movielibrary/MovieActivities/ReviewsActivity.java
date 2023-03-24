package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielibrary.Adapters.FaqRecycleAdapter;
import com.example.movielibrary.Adapters.ReviewsRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.FaqModels.FaqResponseModel;
import com.example.movielibrary.Models.ReviewsModel.ReviewsResponseModel;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.Helper;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ReviewsActivity extends AppCompatActivity {

    private RequestManager requestManager;
    private ImageView imageView_reviewsPoster;
    private TextView textView_reviewsTitle;
    private ConstraintLayout constrainLayout_loadingWrapper;
    private String image, parent, id;
    private RecyclerView recyclerView_reviews;
    private ReviewsRecycleAdapter reviewsRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView_reviews = findViewById(R.id.recyclerView_reviews);
        imageView_reviewsPoster = findViewById(R.id.imageView_reviewsPoster);
        textView_reviewsTitle = findViewById(R.id.textView_reviewsTitle);
        constrainLayout_loadingWrapper = findViewById(R.id.constrainLayout_loadingWrapper);

        requestManager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra(MovieActivitiesDefaults.DATA);
        id = movie_id;
        image = getIntent().getStringExtra(MovieActivitiesDefaults.IMAGE);
        requestManager.getReviewsList(_listener, movie_id);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i;

        if (Objects.equals(parent, DetailsActivity.class.toString())) {
            i = new Intent(this, DetailsActivity.class);
            i.putExtra(MovieActivitiesDefaults.DATA, id);
        }
        else {
            i = new Intent(this, MainActivity.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }

    private final OnMovieResponseListener<ReviewsResponseModel> _listener = new OnMovieResponseListener<ReviewsResponseModel>() {
        @Override
        public void onResponse(ReviewsResponseModel response) {

            if (response == null) {
                Toast.makeText(ReviewsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            if(response.getErrorMessage() != null && !Objects.equals(response.getErrorMessage(), "")){
                Intent intent = new Intent(ReviewsActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(ReviewsActivity.this, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                startActivity(intent);
                return;
            }

            showResults(response);
        }

        @Override
        public void onError(String message) {

            Toast.makeText(ReviewsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(ReviewsResponseModel response) {
        textView_reviewsTitle.setText(response.getFullTitle());

        try {
            Picasso.get().load(image).placeholder(R.drawable.loading).resize(Helper.getScreenWidth(), Helper.getScreenHeight()).into(imageView_reviewsPoster, new Callback() {
                @Override
                public void onSuccess() {
                    displayDetails(response);
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(ReviewsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ReviewsActivity.this, R.string.common_apiException, Toast.LENGTH_LONG).show();
        }

    }

    private void displayDetails(ReviewsResponseModel response) {
        constrainLayout_loadingWrapper.setVisibility(View.GONE);
        recyclerView_reviews.setHasFixedSize(true);

        reviewsRecycleAdapter = new ReviewsRecycleAdapter(ReviewsActivity.this, response.getItems());
        recyclerView_reviews.setLayoutManager(new LinearLayoutManager(ReviewsActivity.this, LinearLayoutManager.VERTICAL, false));

        recyclerView_reviews.setAdapter(reviewsRecycleAdapter);
    }
}