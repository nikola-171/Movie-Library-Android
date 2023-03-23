package com.example.movielibrary.MovieActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.movielibrary.Adapters.MovieDetails.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Database.Entities.MovieDetails;
import com.example.movielibrary.Models.SearchModels.MovieSearchResult;
import com.example.movielibrary.R;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Shared.Helper;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SavedMoviesActivity extends AppCompatActivity implements OnMovieClickListener {

    DBHandler dbHandler;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    ConstraintLayout constrainLayout_loading;
    CardView cardView_noMoviesSaved;

    private List<MovieSearchResult> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_saved_movies);
        initViewElements();
    }

    private void initViewElements() {
        list = new ArrayList<>();

        dbHandler = new DBHandler(SavedMoviesActivity.this);
        cardView_noMoviesSaved = findViewById(R.id.cardView_noMoviesSaved);
        constrainLayout_loading = findViewById(R.id.constrainLayout_loading);
        ArrayList<MovieDetails> movies = dbHandler.readAllMovies();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recyclerView = findViewById(R.id.recyclerView_savedMovies);
        showResult(movies);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_titleAsc:
                this.list.sort(MovieSearchResult.comparatorTitleAsc);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.filter_titleDesc:
                this.list.sort(MovieSearchResult.comparatorTitleDesc);
                adapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.filter_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.savedMoviesActivity_filterMovies));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    private void showResult(ArrayList<MovieDetails> result) {
        if(result.size() > 0){

            for(MovieDetails entity : result){
                MovieSearchResult searchResult = new MovieSearchResult();
                searchResult.setId(entity.getMovieId());
                searchResult.setTitle(entity.getTitle());
                searchResult.setImage(entity.getPoster());

                list.add(searchResult);
            }

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(SavedMoviesActivity.this, Helper.getGridItemsCount()));

            adapter = new HomeRecyclerAdapter(this, list, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);

            constrainLayout_loading.setVisibility(View.VISIBLE);
            constrainLayout_loading.setVisibility(View.GONE);
        }else{
            constrainLayout_loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(SavedMoviesActivity.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, SavedMoviesActivity.class.toString()));
    }
}