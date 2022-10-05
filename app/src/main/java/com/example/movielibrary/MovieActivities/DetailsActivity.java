package com.example.movielibrary.MovieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.movielibrary.Adapters.MovieDetails.CastRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieDetailsSearchListener;
import com.example.movielibrary.MainActivity;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.R;
import com.example.movielibrary.Utils.DBHandler;
import com.example.movielibrary.Utils.RequestManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView textView_movie_title;
    TextView textView_movie_released;
    TextView textView_movie_runtime;
    TextView textView_movie_rating;
    TextView textView_movie_votes;
    ImageView imageView_movie_poster;
    TextView textView_movie_plot;
    RecyclerView recyclerView_movie_cast;
    CastRecyclerAdapter adapter;
    RequestManager requestManager;
    DBHandler dbHandler;
    ImageButton imageButton;
    ScrollView detailsPageContent;
    CardView CardView_search_placeholder;
    String title = "", poster = "", movieId = "";

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

    private void InitViewElements(){
        dbHandler = new DBHandler(DetailsActivity.this);
        imageButton = findViewById(R.id.ImageButton_action);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        detailsPageContent = findViewById(R.id.detailsPageContent);
        detailsPageContent.setVisibility(View.GONE);
        CardView_search_placeholder = findViewById(R.id.CardView_search_placeholder);

        textView_movie_title = findViewById(R.id.textView_movie_name);
        textView_movie_released = findViewById(R.id.textView_movie_released);
        textView_movie_runtime = findViewById(R.id.textView_movie_runtime);
        textView_movie_rating = findViewById(R.id.textView_movie_rating);
        textView_movie_votes = findViewById(R.id.textView_movie_votes);
        imageView_movie_poster = findViewById(R.id.imageView_movie_poster);
        textView_movie_plot = findViewById(R.id.textView_movie_plot);
        recyclerView_movie_cast = findViewById(R.id.recyclerView_movie_cast);

        requestManager = new RequestManager(this);
        String movie_id = getIntent().getStringExtra("data");

        requestManager.searchMovieDetails(_listener, movie_id);
    }


    private final OnMovieDetailsSearchListener _listener = new OnMovieDetailsSearchListener() {
        @Override
        public void onResponse(DetailsMovieResponse response) {

            if (response.equals(null)) {
                Toast.makeText(DetailsActivity.this, R.string.api_error_response, Toast.LENGTH_LONG).show();
                return;
            }

            if(response.getErrorMessage() != null && response.getErrorMessage() != ""){
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
        textView_movie_released.setText(getString(R.string.details_release_date) + " " + response.getReleaseDate());
        textView_movie_runtime.setText(getString(R.string.details_runtime) + " " + response.getRuntimeStr());
        textView_movie_rating.setText(getString(R.string.details_rating) + " " + response.getImDbRating());
        textView_movie_votes.setText(getString(R.string.details_votes) + " " + response.getImDbRatingVotes());
        textView_movie_plot.setText(response.getPlot());

        title = response.getTitle();
        poster = response.getImage();
        movieId = response.getId();

        try {
            Picasso.get().load(response.getImage()).resize(800, 1300).into(imageView_movie_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!dbHandler.IsMovieInDatabase(movieId)){

            imageButton.setImageResource(R.drawable.ic_baseline_save_alt_24);
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
            imageButton.setImageResource(R.drawable.ic_baseline_delete_24);
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

        CardView_search_placeholder.setVisibility(View.GONE);
        detailsPageContent.setVisibility(View.VISIBLE);
    }
}