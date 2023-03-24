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
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.FaqModels.FaqResponseModel;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.Helper;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class FaqActivity extends AppCompatActivity {

    private RequestManager requestManager;
    private ImageView imageView_faqPoster;
    private TextView textView_faqTitle;
    private ConstraintLayout constrainLayout_loadingWrapper;
    private String image, parent, id;
    private RecyclerView recyclerView_questions;
    private FaqRecycleAdapter faqRecycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView_questions = findViewById(R.id.recyclerView_questions);
        imageView_faqPoster = findViewById(R.id.imageView_faqPoster);
        textView_faqTitle = findViewById(R.id.textView_faqTitle);
        constrainLayout_loadingWrapper = findViewById(R.id.constrainLayout_loadingWrapper);

        requestManager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra(MovieActivitiesDefaults.DATA);
        id = movie_id;
        image = getIntent().getStringExtra(MovieActivitiesDefaults.IMAGE);
        requestManager.getFaqList(_listener, movie_id);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }

    }

    private final OnMovieResponseListener<FaqResponseModel> _listener = new OnMovieResponseListener<FaqResponseModel>() {
        @Override
        public void onResponse(FaqResponseModel response) {

            if (response == null) {
                Toast.makeText(FaqActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            if(response.getErrorMessage() != null && !Objects.equals(response.getErrorMessage(), "")){
                Intent intent = new Intent(FaqActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(FaqActivity.this, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                startActivity(intent);
                return;
            }

           showResults(response);
        }

        @Override
        public void onError(String message) {

            Toast.makeText(FaqActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(FaqResponseModel response) {
        textView_faqTitle.setText(response.getFullTitle());

        try {
            Picasso.get().load(image).placeholder(R.drawable.loading).resize(Helper.getScreenWidth(), Helper.getScreenHeight()).into(imageView_faqPoster, new Callback() {
                @Override
                public void onSuccess() {
                    displayDetails(response);
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(FaqActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(FaqActivity.this, R.string.common_apiException, Toast.LENGTH_LONG).show();
        }

    }

    private void displayDetails(FaqResponseModel response) {
        constrainLayout_loadingWrapper.setVisibility(View.GONE);
        recyclerView_questions.setHasFixedSize(true);

        faqRecycleAdapter = new FaqRecycleAdapter(FaqActivity.this, response.getItems());
        recyclerView_questions.setLayoutManager(new LinearLayoutManager(FaqActivity.this, LinearLayoutManager.VERTICAL, false));

        recyclerView_questions.setAdapter(faqRecycleAdapter);
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
}