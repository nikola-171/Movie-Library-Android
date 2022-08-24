package com.example.movielibrary.MovieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.movielibrary.Adapters.MovieDetails.CastRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieDetailsSearchListener;
import com.example.movielibrary.MainActivity;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.R;
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
    ProgressDialog dialog;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_details);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        goToHomePage();
                        break;
                }
                return false;
            }
        });


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

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.show();
        requestManager.searchMovieDetails(_listener, movie_id);
    }

    private void goToHomePage() {
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private OnMovieDetailsSearchListener _listener = new OnMovieDetailsSearchListener() {
        @Override
        public void onResponse(DetailsMovieResponse response) {
            dialog.dismiss();

            if (response.equals(null)) {
                Toast.makeText(DetailsActivity.this, "Error", Toast.LENGTH_LONG).show();
                return;
            }

            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();

            Toast.makeText(DetailsActivity.this, "Error occured", Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(DetailsMovieResponse response) {
        textView_movie_title.setText(response.getTitle());
        textView_movie_released.setText("Year released " + response.getReleaseDate());
        textView_movie_runtime.setText("Runtime: " + response.getRuntimeStr());
        textView_movie_rating.setText("Rating: " + response.getImDbRating());
        textView_movie_votes.setText("Number of votes: " + response.getImDbRatingVotes());
        textView_movie_plot.setText(response.getPlot());

        try {
            Picasso.get().load(response.getImage()).resize(800, 1300).into(imageView_movie_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView_movie_cast.setHasFixedSize(true);
        recyclerView_movie_cast.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CastRecyclerAdapter(this, response.getActorList());
        recyclerView_movie_cast.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}