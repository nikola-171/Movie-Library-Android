package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.Images.ImagesResponseModel;
import com.example.movielibrary.Models.UserRatings.UsersRatingResponseModel;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;

import java.util.Objects;

public class UserRatingsActivity extends AppCompatActivity {

    private String itemId, parent;
    ConstraintLayout constrainLayout_loadingWrapper;

    TextView textView_userRatingsMovieTitle,
            textView_globalRating,
            textView_demographicAllTitle,

    textView_demographicAll_allAges,
            textView_demographicAll_allAges_votes,
            textView_demographicAll_allAges_rating,

    textView_demographicAll_agesUnder18,
            textView_demographicAll_agesUnder18_votes,
            textView_demographicAll_agesUnder18_rating,

    textView_demographicAll_ages18to29,
            textView_demographicAll_ages18to29_votes,
            textView_demographicAll_ages18to29_rating,

    textView_demographicAll_ages30to44,
            textView_demographicAll_ages30to44_votes,
            textView_demographicAll_ages30to44_rating,

    textView_demographicAll_agesOver45,
            textView_demographicAll_agesOver45_votes,
            textView_demographicAll_agesOver45_rating,

    textView_demographicMale_allAges,
            textView_demographicMale_allAges_votes,
            textView_demographicMale_allAges_rating,

    textView_demographicMale_agesUnder18,
            textView_demographicMale_agesUnder18_votes,
            textView_demographicMale_agesUnder18_rating,

    textView_demographicMale_ages18to29,
            textView_demographicMale_ages18to29_votes,
            textView_demographicMale_ages18to29_rating,

    textView_demographicMale_ages30to44,
            textView_demographicMale_ages30to44_votes,
            textView_demographicMale_ages30to44_rating,

    textView_demographicMale_agesOver45,
            textView_demographicMale_agesOver45_votes,
            textView_demographicMale_agesOver45_rating,

    textView_demographicFemale_allAges,
            textView_demographicFemale_allAges_votes,
            textView_demographicFemale_allAges_rating,

    textView_demographicFemale_agesUnder18,
            textView_demographicFemale_agesUnder18_votes,
            textView_demographicFemale_agesUnder18_rating,

    textView_demographicFemale_ages18to29,
            textView_demographicFemale_ages18to29_votes,
            textView_demographicFemale_ages18to29_rating,

    textView_demographicFemale_ages30to44,
            textView_demographicFemale_ages30to44_votes,
            textView_demographicFemale_ages30to44_rating,

    textView_demographicFemale_agesOver45,
            textView_demographicFemale_agesOver45_votes,
            textView_demographicFemale_agesOver45_rating,

    textView_votersOver1000_votes,
            textView_votersOver1000_rating,

    textView_votersUs_votes,
            textView_votersUs_rating,

    textView_votersNonUs_votes,
            textView_votersNonUs_rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ratings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemId = extras.getString(MovieActivitiesDefaults.ID);
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }

        constrainLayout_loadingWrapper = findViewById(R.id.constrainLayout_loadingWrapper);

        textView_userRatingsMovieTitle = findViewById(R.id.textView_userRatingsMovieTitle);
        textView_globalRating = findViewById(R.id.textView_globalRating);
        textView_demographicAllTitle = findViewById(R.id.textView_demographicAllTitle);

        textView_demographicAll_allAges = findViewById(R.id.textView_demographicAll_allAges);
        textView_demographicAll_allAges_votes = findViewById(R.id.textView_demographicAll_allAges_votes);
        textView_demographicAll_allAges_rating = findViewById(R.id.textView_demographicAll_allAges_rating);

        textView_demographicAll_agesUnder18 = findViewById(R.id.textView_demographicAll_agesUnder18);
        textView_demographicAll_agesUnder18_votes = findViewById(R.id.textView_demographicAll_agesUnder18_votes);
        textView_demographicAll_agesUnder18_rating = findViewById(R.id.textView_demographicAll_agesUnder18_rating);

        textView_demographicAll_ages18to29 = findViewById(R.id.textView_demographicAll_ages18to29);
        textView_demographicAll_ages18to29_votes = findViewById(R.id.textView_demographicAll_ages18to29_votes);
        textView_demographicAll_ages18to29_rating = findViewById(R.id.textView_demographicAll_ages18to29_rating);

        textView_demographicAll_ages30to44 = findViewById(R.id.textView_demographicAll_ages30to44);
        textView_demographicAll_ages30to44_votes = findViewById(R.id.textView_demographicAll_ages30to44_votes);
        textView_demographicAll_ages30to44_rating = findViewById(R.id.textView_demographicAll_ages30to44_rating);

        textView_demographicAll_agesOver45 = findViewById(R.id.textView_demographicAll_agesOver45);
        textView_demographicAll_agesOver45_votes = findViewById(R.id.textView_demographicAll_agesOver45_votes);
        textView_demographicAll_agesOver45_rating = findViewById(R.id.textView_demographicAll_agesOver45_rating);

        textView_demographicMale_allAges = findViewById(R.id.textView_demographicMale_allAges);
        textView_demographicMale_allAges_votes = findViewById(R.id.textView_demographicMale_allAges_votes);
        textView_demographicMale_allAges_rating = findViewById(R.id.textView_demographicMale_allAges_rating);

        textView_demographicMale_agesUnder18 = findViewById(R.id.textView_demographicMale_agesUnder18);
        textView_demographicMale_agesUnder18_votes = findViewById(R.id.textView_demographicMale_agesUnder18_votes);
        textView_demographicMale_agesUnder18_rating = findViewById(R.id.textView_demographicMale_agesUnder18_rating);

        textView_demographicMale_ages18to29 = findViewById(R.id.textView_demographicMale_ages18to29);
        textView_demographicMale_ages18to29_votes = findViewById(R.id.textView_demographicMale_ages18to29_votes);
        textView_demographicMale_ages18to29_rating = findViewById(R.id.textView_demographicMale_ages18to29_rating);

        textView_demographicMale_ages30to44 = findViewById(R.id.textView_demographicMale_ages30to44);
        textView_demographicMale_ages30to44_votes = findViewById(R.id.textView_demographicMale_ages30to44_votes);
        textView_demographicMale_ages30to44_rating = findViewById(R.id.textView_demographicMale_ages30to44_rating);

        textView_demographicMale_agesOver45 = findViewById(R.id.textView_demographicMale_agesOver45);
        textView_demographicMale_agesOver45_votes = findViewById(R.id.textView_demographicMale_agesOver45_votes);
        textView_demographicMale_agesOver45_rating = findViewById(R.id.textView_demographicMale_agesOver45_rating);

        textView_demographicFemale_allAges = findViewById(R.id.textView_demographicFemale_allAges);
        textView_demographicFemale_allAges_votes = findViewById(R.id.textView_demographicFemale_allAges_votes);
        textView_demographicFemale_allAges_rating = findViewById(R.id.textView_demographicFemale_allAges_rating);

        textView_demographicFemale_agesUnder18 = findViewById(R.id.textView_demographicFemale_agesUnder18);
        textView_demographicFemale_agesUnder18_votes = findViewById(R.id.textView_demographicFemale_agesUnder18_votes);
        textView_demographicFemale_agesUnder18_rating = findViewById(R.id.textView_demographicFemale_agesUnder18_rating);

        textView_demographicFemale_ages18to29 = findViewById(R.id.textView_demographicFemale_ages18to29);
        textView_demographicFemale_ages18to29_votes = findViewById(R.id.textView_demographicFemale_ages18to29_votes);
        textView_demographicFemale_ages18to29_rating = findViewById(R.id.textView_demographicFemale_ages18to29_rating);

        textView_demographicFemale_ages30to44 = findViewById(R.id.textView_demographicFemale_ages30to44);
        textView_demographicFemale_ages30to44_votes = findViewById(R.id.textView_demographicFemale_ages30to44_votes);
        textView_demographicFemale_ages30to44_rating = findViewById(R.id.textView_demographicFemale_ages30to44_rating);

        textView_demographicFemale_agesOver45 = findViewById(R.id.textView_demographicFemale_agesOver45);
        textView_demographicFemale_agesOver45_votes = findViewById(R.id.textView_demographicFemale_agesOver45_votes);
        textView_demographicFemale_agesOver45_rating = findViewById(R.id.textView_demographicFemale_agesOver45_rating);

        textView_votersOver1000_votes = findViewById(R.id.textView_votersOver1000_votes);
        textView_votersOver1000_rating = findViewById(R.id.textView_votersOver1000_rating);

        textView_votersUs_votes = findViewById(R.id.textView_votersUs_votes);
        textView_votersUs_rating = findViewById(R.id.textView_votersUs_rating);

        textView_votersNonUs_votes = findViewById(R.id.textView_votersNonUs_votes);
        textView_votersNonUs_rating = findViewById(R.id.textView_votersNonUs_rating);

        RequestManager requestManager = new RequestManager(UserRatingsActivity.this);
        requestManager.getUsersRatings(_listener, itemId);
    }

    private final OnMovieResponseListener<UsersRatingResponseModel> _listener = new OnMovieResponseListener<UsersRatingResponseModel>() {
        @Override
        public void onResponse(UsersRatingResponseModel response) {

            if (response == null) {
                Toast.makeText(UserRatingsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            if (response.getErrorMessage() != null && !Objects.equals(response.getErrorMessage(), "")) {
                Intent intent = new Intent(UserRatingsActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserRatingsActivity.this, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                startActivity(intent);
                return;
            }

            showResults(response);
        }

        @Override
        public void onError(String message) {

            Toast.makeText(UserRatingsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(UsersRatingResponseModel response) {

        textView_userRatingsMovieTitle.setText(response.getFullTitle());
        textView_globalRating.setText(response.getTotalRating());

        textView_demographicAll_allAges_votes.setText(response.getDemographicAll().getAllAges().getVotes());
        textView_demographicAll_allAges_rating.setText(response.getDemographicAll().getAllAges().getRating());

        textView_demographicAll_agesUnder18_votes.setText(response.getDemographicAll().getAgesUnder18().getVotes());
        textView_demographicAll_agesUnder18_rating.setText(response.getDemographicAll().getAgesUnder18().getRating());

        textView_demographicAll_ages18to29_votes.setText(response.getDemographicAll().getAges18To29().getVotes());
        textView_demographicAll_ages18to29_rating.setText(response.getDemographicAll().getAges18To29().getRating());

        textView_demographicAll_ages30to44_votes.setText(response.getDemographicAll().getAges30To44().getVotes());
        textView_demographicAll_ages30to44_rating.setText(response.getDemographicAll().getAges30To44().getRating());

        textView_demographicAll_agesOver45_votes.setText(response.getDemographicAll().getAgesOver45().getVotes());
        textView_demographicAll_agesOver45_rating.setText(response.getDemographicAll().getAgesOver45().getRating());

        textView_demographicMale_allAges_votes.setText(response.getDemographicMales().getAllAges().getVotes());
        textView_demographicMale_allAges_rating.setText(response.getDemographicMales().getAllAges().getRating());

        textView_demographicMale_agesUnder18_votes.setText(response.getDemographicMales().getAgesUnder18().getVotes());
        textView_demographicMale_agesUnder18_rating.setText(response.getDemographicMales().getAgesUnder18().getRating());

        textView_demographicMale_ages18to29_votes.setText(response.getDemographicMales().getAges18To29().getVotes());
        textView_demographicMale_ages18to29_rating.setText(response.getDemographicMales().getAges18To29().getRating());

        textView_demographicMale_ages30to44_votes.setText(response.getDemographicMales().getAges30To44().getVotes());
        textView_demographicMale_ages30to44_rating.setText(response.getDemographicMales().getAges30To44().getRating());

        textView_demographicMale_agesOver45_votes.setText(response.getDemographicMales().getAgesOver45().getVotes());
        textView_demographicMale_agesOver45_rating.setText(response.getDemographicMales().getAgesOver45().getRating());

        textView_demographicFemale_allAges_votes.setText(response.getDemographicFemales().getAllAges().getVotes());
        textView_demographicFemale_allAges_rating.setText(response.getDemographicFemales().getAllAges().getRating());

        textView_demographicFemale_agesUnder18_votes.setText(response.getDemographicFemales().getAgesUnder18().getVotes());
        textView_demographicFemale_agesUnder18_rating.setText(response.getDemographicFemales().getAgesUnder18().getRating());

        textView_demographicFemale_ages18to29_votes.setText(response.getDemographicFemales().getAges18To29().getVotes());
        textView_demographicFemale_ages18to29_rating.setText(response.getDemographicFemales().getAges18To29().getRating());

        textView_demographicFemale_ages30to44_votes.setText(response.getDemographicFemales().getAges30To44().getVotes());
        textView_demographicFemale_ages30to44_rating .setText(response.getDemographicFemales().getAges30To44().getRating());

        textView_demographicFemale_agesOver45_votes.setText(response.getDemographicFemales().getAgesOver45().getVotes());
        textView_demographicFemale_agesOver45_rating.setText(response.getDemographicFemales().getAgesOver45().getRating());

        textView_votersOver1000_votes.setText(response.getTop1000Voters().getVotes());
        textView_votersOver1000_rating.setText(response.getTop1000Voters().getRating());

        textView_votersUs_votes.setText(response.getUsUsers().getVotes());
        textView_votersUs_rating.setText(response.getUsUsers().getRating());

        textView_votersNonUs_votes.setText(response.getNonUSUsers().getVotes());
        textView_votersNonUs_rating.setText(response.getNonUSUsers().getRating());

        constrainLayout_loadingWrapper.setVisibility(View.GONE);
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
            i.putExtra(MovieActivitiesDefaults.DATA, itemId);
        } else {
            i = new Intent(this, MainActivity.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }
}