package com.example.movielibrary.MovieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.movielibrary.Adapters.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.MainActivity;
import com.example.movielibrary.Models.Database.MovieDetailsEntity;
import com.example.movielibrary.Models.SearchModels.MovieSearchResult;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.R;
import com.example.movielibrary.Utils.DBHandler;
import com.example.movielibrary.Utils.RequestManager;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SavedMoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    DBHandler dbHandler;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    CardView CardView_displayMovies, CardView_noMoviesSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_saved_movies);
        InitViewElements();
    }

    private void InitViewElements() {
        dbHandler = new DBHandler(SavedMoviesActivity.this);
        CardView_displayMovies = findViewById(R.id.CardView_savedMovies);
        CardView_noMoviesSaved = findViewById(R.id.CardView_noMoviesSaved);

        ArrayList<MovieDetailsEntity> movies = dbHandler.readAllMovies();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = findViewById(R.id.recycler_view_saved_movies);
        showResult(movies);

    }


    private void showResult(ArrayList<MovieDetailsEntity> result) {
        if(result.size() > 0){
            ArrayList<MovieSearchResult> list = new ArrayList<>();

            for(MovieDetailsEntity entity : result){
                MovieSearchResult searchResult = new MovieSearchResult();
                searchResult.setId(entity.getMovieId());
                searchResult.setTitle(entity.getTitle());
                searchResult.setImage(entity.getPoster());

                list.add(searchResult);
            }
            recyclerView.setHasFixedSize(true);
            int spanCount = result.size() > 1 ? 2 : 1;
            recyclerView.setLayoutManager(new GridLayoutManager(SavedMoviesActivity.this, spanCount));

            adapter = new HomeRecyclerAdapter(this, list, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);

            CardView_displayMovies.setVisibility(View.VISIBLE);
            CardView_noMoviesSaved.setVisibility(View.GONE);
        }else{
            CardView_displayMovies.setVisibility(View.GONE);
            CardView_noMoviesSaved.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(SavedMoviesActivity.this, DetailsActivity.class)
                .putExtra("data", id));
    }

    private void goToHomePage() {
        Intent intent = new Intent(SavedMoviesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}