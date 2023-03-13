package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielibrary.Adapters.MovieDetails.CastRecyclerAdapter;
import com.example.movielibrary.Adapters.MovieDetails.SimilarMoviesRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieDetailsSearchListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.MovieActivities.TopLists.BoxOfficeAllTime;
import com.example.movielibrary.MovieActivities.TopLists.Top250Movies;
import com.example.movielibrary.R;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity implements OnMovieClickListener {

    TextView textView_movie_title;
    TextView textView_movie_released;
    TextView textView_movie_runtime;
    TextView textView_movie_rating;
    TextView textView_movie_votes;
    TextView textView_awards;
    TextView textView_genres;
    TextView textView_companies;
    TextView textView_languages;
    TextView textView_keywords;
    TextView textView_movie_plot;
    ImageView imageView_movie_poster;
    RecyclerView recyclerView_movie_cast;
    RecyclerView recyclerView_similarMovies;
    CastRecyclerAdapter adapter;
    SimilarMoviesRecycleAdapter similarMoviesAdapter;
    ConstraintLayout constrainLayout_loadingWrapper;
    RequestManager requestManager;
    DBHandler dbHandler;
    ImageButton imageButton;
    ScrollView detailsPageContent;
    CardView cardView_searchPlaceholder;
    String title, poster, movieId , parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_details);
        initViewElements();
    }

    private void displaySavedMovies() {
        Intent intent = new Intent(DetailsActivity.this, SavedMoviesActivity.class);
        startActivity(intent);
        finish();
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

        if (Objects.equals(parent, MainActivity.class.toString())) {
            i = new Intent(this, MainActivity.class);
        }
        else if(Objects.equals(parent, Top250Movies.class.toString())){
            i = new Intent(this, Top250Movies.class);
        }
        else if(Objects.equals(parent, DetailsActivity.class.toString())){
            i = new Intent(this, DetailsActivity.class);
        }
        else if (Objects.equals(parent, SavedMoviesActivity.class.toString())){
            i = new Intent(this, SavedMoviesActivity.class);
        }
        else {
            i = new Intent(this, BoxOfficeAllTime.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }

    private void initViewElements(){
        dbHandler = new DBHandler(DetailsActivity.this);
        imageButton = findViewById(R.id.imageButton_action);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        constrainLayout_loadingWrapper = findViewById(R.id.constrainLayout_loadingWrapper);
        detailsPageContent = findViewById(R.id.detailsPageContent);
        cardView_searchPlaceholder = findViewById(R.id.cardView_searchPlaceholder);
        textView_movie_title = findViewById(R.id.textView_movieName);
        textView_movie_released = findViewById(R.id.textView_movieReleased);
        textView_movie_runtime = findViewById(R.id.textView_movieRuntime);
        textView_movie_rating = findViewById(R.id.textView_movieRating);
        textView_movie_votes = findViewById(R.id.textView_movieVotes);
        imageView_movie_poster = findViewById(R.id.imageView_moviePoster);
        textView_movie_plot = findViewById(R.id.textView_moviePlot);
        recyclerView_movie_cast = findViewById(R.id.recyclerView_movieCast);
        recyclerView_similarMovies = findViewById(R.id.recyclerView_similarMovies);
        textView_awards = findViewById(R.id.textView_awards);
        textView_genres = findViewById(R.id.textView_genres);
        textView_languages = findViewById(R.id.textView_languages);
        textView_keywords = findViewById(R.id.textView_keywords);
        detailsPageContent.setVisibility(View.GONE);
        textView_movie_title.setTextColor(ContextCompat.getColor(DetailsActivity.this, R.color.white));
        textView_movie_plot.setTextColor(ContextCompat.getColor(DetailsActivity.this, R.color.white));
        textView_companies = findViewById(R.id.textView_companies);
        requestManager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra(MovieActivitiesDefaults.DATA);

        requestManager.searchMovieDetails(_listener, movie_id);
    }


    private final OnMovieResponseListener<DetailsMovieResponse> _listener = new OnMovieResponseListener<DetailsMovieResponse>() {
        @Override
        public void onResponse(DetailsMovieResponse response) {

            if (response == null) {
                Toast.makeText(DetailsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            if(response.getErrorMessage() != null && !Objects.equals(response.getErrorMessage(), "")){
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(DetailsActivity.this, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                startActivity(intent);
                return;
            }

            showResults(response);
        }

        @Override
        public void onError(String message) {

            Toast.makeText(DetailsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(DetailsMovieResponse response) {
        textView_movie_title.setText(response.getTitle());
        textView_movie_released.setText(String.format("%s %s", getString(R.string.movieDetails_releaseDate), response.getReleaseDate()));
        textView_movie_runtime.setText(String.format("%s %s", getString(R.string.movieDetails_runtime), response.getRuntimeStr()));
        textView_movie_rating.setText(String.format("%s %s", getString(R.string.movieDetails_rating), response.getImDbRating()));
        textView_movie_votes.setText(String.format("%s %s", getString(R.string.movieDetails_votes), response.getImDbRatingVotes()));
        textView_awards.setText(String.format("%s %s", getString(R.string.detailsActivity_awards), response.getAwards()));
        textView_genres.setText(String.format("%s %s", getString(R.string.detailsActivity_genres), response.getGenres()));
        textView_movie_plot.setText(response.getPlot());
        textView_companies.setText(String.format("%s %s", getString(R.string.detailsActivity_companies), response.getCompanies()));
        textView_languages.setText(String.format("%s %s", getString(R.string.detailsActivity_languages), response.getLanguages()));
        textView_keywords.setText(String.format("%s %s", getString(R.string.detailsActivity_keywords), response.getKeywords()));

        title = response.getTitle();
        poster = response.getImage();
        movieId = response.getId();

        try {
            Picasso.get().load(response.getImage()).placeholder(R.drawable.loading).resize(900, 1560).into(imageView_movie_poster, new Callback() {
                @Override
                public void onSuccess() {
                    displayMovieDetails(response);
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(DetailsActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                    displayMovieDetails(response);
                }
            });
        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this, R.string.common_apiException, Toast.LENGTH_LONG).show();
            startActivity(getParentActivityIntent());
        }

        if(!dbHandler.isMovieInDatabase(movieId)){

            imageButton.setImageResource(R.drawable.ic_baseline_save_50);
            imageButton.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle(R.string.movieDetails_saveMovieTitle);
                builder.setMessage(R.string.movieDetails_saveMovieMessage);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.common_yes, (dialogInterface, i) -> {
                    if(dbHandler.isMovieInDatabase(movieId)){
                        Toast.makeText(DetailsActivity.this, R.string.movieDetails_movieAlreadySaved, Toast.LENGTH_LONG).show();
                    }else {
                        dbHandler.addNewMovie(title, poster, movieId);
                        Toast.makeText(DetailsActivity.this, R.string.movieDetails_movieSavedSuccess, Toast.LENGTH_LONG).show();
                        displaySavedMovies();
                    }
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton(R.string.common_no, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        }else{
            imageButton.setImageResource(R.drawable.ic_baseline_delete_forever_50);
            imageButton.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle(R.string.movieDetails_dialogDeleteTitle);
                builder.setMessage(R.string.movieDetails_dialogDeleteMessage);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.common_yes, (dialogInterface, i) -> {
                    if(!dbHandler.isMovieInDatabase(movieId)){
                        Toast.makeText(DetailsActivity.this, R.string.movieDetails_moveDeleted, Toast.LENGTH_LONG).show();
                    }else{
                        dbHandler.deleteMovie(movieId);
                        Toast.makeText(DetailsActivity.this, R.string.movieDetails_movieDeletedSuccess, Toast.LENGTH_LONG).show();
                        displaySavedMovies();
                    }
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton(R.string.common_no, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        }


    }

    private void displayMovieDetails(DetailsMovieResponse response){
        recyclerView_movie_cast.setHasFixedSize(true);
        recyclerView_movie_cast.setLayoutManager(new GridLayoutManager(DetailsActivity.this, 1));
        adapter = new CastRecyclerAdapter(DetailsActivity.this, response.getActorList());
        recyclerView_movie_cast.setAdapter(adapter);
        recyclerView_similarMovies.setHasFixedSize(true);
        recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        similarMoviesAdapter = new SimilarMoviesRecycleAdapter(DetailsActivity.this, response.getSimilars(), DetailsActivity.this);
        recyclerView_similarMovies.setAdapter(similarMoviesAdapter);
        constrainLayout_loadingWrapper.setVisibility(View.GONE);
        detailsPageContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(DetailsActivity.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, DetailsActivity.class.toString()));
    }
}