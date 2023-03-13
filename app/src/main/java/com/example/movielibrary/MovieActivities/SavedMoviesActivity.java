package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.movielibrary.Adapters.MovieDetails.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Database.Entities.MovieDetails;
import com.example.movielibrary.Models.SearchModels.MovieSearchResult;
import com.example.movielibrary.R;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;

import java.util.ArrayList;

public class SavedMoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    DBHandler dbHandler;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    CardView cardView_displayMovies, cardView_noMoviesSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_saved_movies);
        initViewElements();
    }

    private void initViewElements() {
        dbHandler = new DBHandler(SavedMoviesActivity.this);
        cardView_displayMovies = findViewById(R.id.cardView_savedMovies);
        cardView_noMoviesSaved = findViewById(R.id.cardView_noMoviesSaved);

        ArrayList<MovieDetails> movies = dbHandler.readAllMovies();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = findViewById(R.id.recyclerView_savedMovies);
        showResult(movies);

    }


    private void showResult(ArrayList<MovieDetails> result) {
        if(result.size() > 0){
            ArrayList<MovieSearchResult> list = new ArrayList<>();

            for(MovieDetails entity : result){
                MovieSearchResult searchResult = new MovieSearchResult();
                searchResult.setId(entity.getMovieId());
                searchResult.setTitle(entity.getTitle());
                searchResult.setImage(entity.getPoster());

                list.add(searchResult);
            }
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(SavedMoviesActivity.this, 2));

            adapter = new HomeRecyclerAdapter(this, list, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);

            cardView_displayMovies.setVisibility(View.VISIBLE);
            cardView_noMoviesSaved.setVisibility(View.GONE);
        }else{
            cardView_displayMovies.setVisibility(View.GONE);
            cardView_noMoviesSaved.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(SavedMoviesActivity.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, SavedMoviesActivity.class.toString()));
    }
}