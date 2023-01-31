package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.example.movielibrary.MainActivity;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.R;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;
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

    ImageView imageView_movie_poster;
    TextView textView_movie_plot;
    RecyclerView recyclerView_movie_cast;
    RecyclerView recyclerView_similarMovies;
    CastRecyclerAdapter adapter;
    SimilarMoviesRecycleAdapter similarMoviesAdapter;

    RequestManager requestManager;
    DBHandler dbHandler;
    ImageButton imageButton;
    ScrollView detailsPageContent;
    CardView CardView_search_placeholder;
    String title = "", poster = "", movieId = "";
    String parent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_details);
        InitViewElements();
    }

    private void displaySavedMovies() {
        Intent intent = new Intent(DetailsActivity.this, SavedMoviesActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToHomePage() {
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
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
        Intent i = null;

        if (Objects.equals(parent, MainActivity.class.toString())) {
            i = new Intent(this, MainActivity.class);
        } else {
            i = new Intent(this, SavedMoviesActivity.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }

    private void InitViewElements(){
        dbHandler = new DBHandler(DetailsActivity.this);
        imageButton = findViewById(R.id.ImageButton_action);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
            //The key argument here must match that used in the other activity
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        detailsPageContent = findViewById(R.id.detailsPageContent);
        CardView_search_placeholder = findViewById(R.id.CardView_search_placeholder);
        textView_movie_title = findViewById(R.id.textView_movie_name);
        textView_movie_released = findViewById(R.id.textView_movie_released);
        textView_movie_runtime = findViewById(R.id.textView_movie_runtime);
        textView_movie_rating = findViewById(R.id.textView_movie_rating);
        textView_movie_votes = findViewById(R.id.textView_movie_votes);
        imageView_movie_poster = findViewById(R.id.imageView_movie_poster);
        textView_movie_plot = findViewById(R.id.textView_movie_plot);
        recyclerView_movie_cast = findViewById(R.id.recyclerView_movie_cast);
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


    private final OnMovieDetailsSearchListener _listener = new OnMovieDetailsSearchListener() {
        @Override
        public void onResponse(DetailsMovieResponse response) {

            if (response == null) {
                Toast.makeText(DetailsActivity.this, R.string.api_error_response, Toast.LENGTH_LONG).show();
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

            Toast.makeText(DetailsActivity.this, R.string.api_error_response, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(DetailsMovieResponse response) {
        textView_movie_title.setText(response.getTitle());
        textView_movie_released.setText(String.format("%s %s", getString(R.string.details_release_date), response.getReleaseDate()));
        textView_movie_runtime.setText(String.format("%s %s", getString(R.string.details_runtime), response.getRuntimeStr()));
        textView_movie_rating.setText(String.format("%s %s", getString(R.string.details_rating), response.getImDbRating()));
        textView_movie_votes.setText(String.format("%s %s", getString(R.string.details_votes), response.getImDbRatingVotes()));
        textView_awards.setText(String.format("%s %s", getString(R.string.DetailsActivity_awards), response.getAwards()));
        textView_genres.setText(String.format("%s %s", getString(R.string.DetailsActivity_genres), response.getGenres()));
        textView_movie_plot.setText(response.getPlot());
        textView_companies.setText(String.format("%s %s", getString(R.string.DetailsActivity_companies), response.getCompanies()));
        textView_languages.setText(String.format("%s %s", getString(R.string.DetailsActivity_languages), response.getLanguages()));
        textView_keywords.setText(String.format("%s %s", getString(R.string.DetailsActivity_keywords), response.getKeywords()));

        title = response.getTitle();
        poster = response.getImage();
        movieId = response.getId();

        try {
            Picasso.get().load(response.getImage()).resize(900, 1560).into(imageView_movie_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!dbHandler.IsMovieInDatabase(movieId)){

            imageButton.setImageResource(R.drawable.ic_baseline_save_50);
            imageButton.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle(R.string.dialog_saving_title);
                builder.setMessage(R.string.dialog_saving_message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.dialog_yes, (dialogInterface, i) -> {
                    if(dbHandler.IsMovieInDatabase(movieId)){
                        Toast.makeText(DetailsActivity.this, R.string.details_already_saved, Toast.LENGTH_LONG).show();
                    }else {
                        dbHandler.addNewMovie(title, poster, movieId);
                        Toast.makeText(DetailsActivity.this, R.string.details_save_movie_success, Toast.LENGTH_LONG).show();
                        displaySavedMovies();
                    }
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton(R.string.dialog_no, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        }else{
            imageButton.setImageResource(R.drawable.ic_baseline_delete_forever_50);
            imageButton.setOnClickListener(view -> {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle(R.string.dialog_deleting_title);
                builder.setMessage(R.string.dialog_deleting_message);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.dialog_yes, (dialogInterface, i) -> {
                    if(!dbHandler.IsMovieInDatabase(movieId)){
                        Toast.makeText(DetailsActivity.this, R.string.details_movie_deleted_message, Toast.LENGTH_LONG).show();
                    }else{
                        dbHandler.deleteMovie(movieId);
                        Toast.makeText(DetailsActivity.this, R.string.details_saved_movie_delete_success, Toast.LENGTH_LONG).show();
                        displaySavedMovies();
                    }
                    dialogInterface.dismiss();
                });
                builder.setNegativeButton(R.string.dialog_no, (dialogInterface, i) -> dialogInterface.dismiss());
                builder.show();
            });
        }

        recyclerView_movie_cast.setHasFixedSize(true);
        recyclerView_movie_cast.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CastRecyclerAdapter(this, response.getActorList());
        recyclerView_movie_cast.setAdapter(adapter);


        recyclerView_similarMovies.setHasFixedSize(true);
        recyclerView_similarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similarMoviesAdapter = new SimilarMoviesRecycleAdapter(this, response.getSimilars(), this);

        recyclerView_similarMovies.setAdapter(similarMoviesAdapter);

        CardView_search_placeholder.setVisibility(View.GONE);
        detailsPageContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(DetailsActivity.this, DetailsActivity.class)
                .putExtra("data", id).putExtra("parent", "main"));
    }
}