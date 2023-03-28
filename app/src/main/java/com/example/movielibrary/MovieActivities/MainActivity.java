package com.example.movielibrary.MovieActivities;

import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.resultCode;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.movielibrary.Adapters.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.MovieSearchResult;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.MovieActivities.TopLists.BoxOffice;
import com.example.movielibrary.MovieActivities.TopLists.BoxOfficeAllTime;
import com.example.movielibrary.MovieActivities.TopLists.ComingSoon;
import com.example.movielibrary.MovieActivities.TopLists.InTheaters;
import com.example.movielibrary.MovieActivities.TopLists.MostPopularMovies;
import com.example.movielibrary.MovieActivities.TopLists.MostPopularTvs;
import com.example.movielibrary.MovieActivities.TopLists.Top250Movies;
import com.example.movielibrary.MovieActivities.TopLists.Top250TvsList;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Shared.SearchType;
import com.example.movielibrary.Utils.RequestManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Objects;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    SearchView searchView;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    RequestManager requestManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DBHandler dbHandler;
    LottieAnimationView searchPlaceholderAnimation;
    CardView cardView_searchView;
    ImageView imageView_advancedSearch;
    ConstraintLayout constraintLayout_searchAnimation;
    SearchType searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        initViewElements();
    }

    private void initViewElements(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_open, R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dbHandler = new DBHandler(MainActivity.this);

        searchPlaceholderAnimation = findViewById(R.id.searchPlaceholderAnimation);
        cardView_searchView = findViewById(R.id.cardView_searchView);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView_home);
        requestManager = new RequestManager(this);
        recyclerView.setVisibility(View.GONE);
        constraintLayout_searchAnimation = findViewById(R.id.constraintLayout_searchAnimation);
        imageView_advancedSearch = findViewById(R.id.ImageView_advancedSearch);
        imageView_advancedSearch.setOnClickListener(this::openAdvancedSearchForm);

        searchType = SearchType.SEARCH_DEFAULT;

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_saved) {
                displaySavedMovies();
            }

            if (item.getItemId() == R.id.nav_top250Movies) {
                displayTop250Movies();
            }

            if (item.getItemId() == R.id.nav_topTvs) {
                displayTopTvs();
            }

            if (item.getItemId() == R.id.nav_mostPopularMovies) {
                displayMostPopularMovies();
            }

            if (item.getItemId() == R.id.nav_mostPopularTvs) {
                displayMostPopularTvs();
            }

            if (item.getItemId() == R.id.nav_inTheaters) {
                displayInTheaters();
            }

            if (item.getItemId() == R.id.nav_comingSoon) {
                displayComingSoon();
            }

            if (item.getItemId() == R.id.nav_boxOffice) {
                displayBoxOffice();
            }

            if (item.getItemId() == R.id.nav_boxOfficeAllTime) {
                displayBoxOfficeAllTime();
            }

            if (item.getItemId() == R.id.nav_settings) {
                displaySettings();
            }

            return false;
        });

        EditText txtSearch = (searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(R.string.mainActivity_searchDefault);
        txtSearch.setHintTextColor(Color.WHITE);
        txtSearch.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                cardView_searchView.setVisibility(View.GONE);

                // we call the api
                showLoadingAnimation();

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                requestManager.searchMovies(listener, query, searchType);
                constraintLayout_searchAnimation.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void openAdvancedSearchForm(View view) {
        Intent i = new Intent(MainActivity.this, AdvancedFilterForm.class);
        activityResult.launch(i);
    }

    private final OnMovieResponseListener<SearchResult> listener = new OnMovieResponseListener<SearchResult>() {
        @Override
        public void onResponse(SearchResult result) {
            cardView_searchView.setVisibility(View.VISIBLE);

            if(result == null || (result.getItems() != null && result.getItems().size() <= 0)){
                hideLoadingAnimation();
                Toast.makeText(MainActivity.this, R.string.homePage_noDataFound, Toast.LENGTH_LONG).show();
                return;
            }

            if(result.getErrorMessage() != null && !result.getErrorMessage().equals("")){
                hideLoadingAnimation();

                Toast.makeText(MainActivity.this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            if(result.getItems() == null){
                hideLoadingAnimation();
                Toast.makeText(MainActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            showResult(result);
        }

        @Override
        public void onError(String error) {
            hideLoadingAnimation();
            Toast.makeText(MainActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResult(SearchResult result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        adapter = new HomeRecyclerAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        constraintLayout_searchAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cardView_searchView.setVisibility(View.VISIBLE);

        toggleInputFields(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()) {
            case R.id.searchMovies:
                this.searchType = SearchType.SEARCH_MOVIES;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchMovies));
                return true;

            case R.id.searchSeries:
                this.searchType = SearchType.SEARCH_SERIES;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchSeries));

                return true;

            case R.id.searchNames:
                this.searchType = SearchType.SEARCH_NAMES;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchNames));

                return true;

            case R.id.searchKeyword:
                this.searchType = SearchType.SEARCH_KEYWORD;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchKeywords));

                return true;

            case R.id.searchCompany:
                this.searchType = SearchType.SEARCH_COMPANY;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchCompanies));

                return true;

            case R.id.searchAll:
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchAll));

                this.searchType = SearchType.SEARCH_ALL;
                return true;

            case R.id.searchEpisodes:
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchEpisodes));

                this.searchType = SearchType.SEARCH_EPISODES;
                return true;

            default:
                this.searchType = SearchType.SEARCH_DEFAULT;
                this.searchView.setQueryHint(getString(R.string.mainActivity_searchDefault));

                return super.onOptionsItemSelected(item);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_options_menu, menu);

        return true;
    }

    private void displaySavedMovies() {
        Intent intent = new Intent(MainActivity.this, SavedMoviesActivity.class);
        startActivity(intent);
    }

    private void displayTop250Movies() {
        Intent intent = new Intent(MainActivity.this, Top250Movies.class);
        startActivity(intent);
    }

    private void displayTopTvs() {
        Intent intent = new Intent(MainActivity.this, Top250TvsList.class);
        startActivity(intent);
    }

    private void displayMostPopularMovies() {
        Intent intent = new Intent(MainActivity.this, MostPopularMovies.class);
        startActivity(intent);
    }

    private void displayMostPopularTvs() {
        Intent intent = new Intent(MainActivity.this, MostPopularTvs.class);
        startActivity(intent);
    }

    private void displayInTheaters() {
        Intent intent = new Intent(MainActivity.this, InTheaters.class);
        startActivity(intent);
    }

    private void displayComingSoon() {
        Intent intent = new Intent(MainActivity.this, ComingSoon.class);
        startActivity(intent);
    }

    private void displayBoxOffice() {
        Intent intent = new Intent(MainActivity.this, BoxOffice.class);
        startActivity(intent);
    }

    private void displayBoxOfficeAllTime() {
        Intent intent = new Intent(MainActivity.this, BoxOfficeAllTime.class);
        startActivity(intent);
    }

    private void displaySettings() {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
        .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, MainActivity.class.toString()));
    }

    private void showLoadingAnimation(){
        searchPlaceholderAnimation.setAnimation(R.raw.loading);
        searchPlaceholderAnimation.setRepeatCount(LottieDrawable.INFINITE);
        searchPlaceholderAnimation.playAnimation();
        toggleInputFields(false);

    }

    private void hideLoadingAnimation(){
        searchPlaceholderAnimation.setAnimation(R.raw.watch);
        searchPlaceholderAnimation.setRepeatCount(0);
        searchPlaceholderAnimation.playAnimation();

        toggleInputFields(true);
    }

    private void toggleInputFields(boolean val){
        searchView.setFocusable(val);
        imageView_advancedSearch.setEnabled(val);
    }

    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == resultCode){
                    Intent i = result.getData();

                    if(i != null){
                        HashMap<String, Object> args = (HashMap<String, Object>) i.getSerializableExtra("data");

                        cardView_searchView.setVisibility(View.GONE);
                        showLoadingAnimation();
                        searchView.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        requestManager.advancedSearchMovies(listener, args);
                        constraintLayout_searchAnimation.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }
            });
}