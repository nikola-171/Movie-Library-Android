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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.movielibrary.Adapters.MovieDetails.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.onSearchMoviesListener;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    SearchView searchView;
    RecyclerView recyclerView;
    HomeRecyclerAdapter adapter;
    RequestManager requestManager;
    CardView CardView_search_placeholder;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DBHandler dbHandler;
    LottieAnimationView loadingAnimation, searchPlaceholderAnimation;
    CardView loadingAnimationCard, displayMoviesCardView;
    ImageView ImageView_advancedSearch;
    ConstraintLayout ConstraintLayout_searchAnimation;

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
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Menu_Open, R.string.Menu_Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dbHandler = new DBHandler(MainActivity.this);

        loadingAnimation = findViewById(R.id.animationLoadingView);
        loadingAnimationCard = findViewById(R.id.loadingCardView);
        displayMoviesCardView = findViewById(R.id.displayMoviesCardView);
        loadingAnimationCard.setVisibility(View.GONE);
        searchPlaceholderAnimation = findViewById(R.id.searchPlaceholderAnimation);

        CardView_search_placeholder = findViewById(R.id.CardView_search_placeholder);
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view_home);
        requestManager = new RequestManager(this);
        recyclerView.setVisibility(View.GONE);
        ConstraintLayout_searchAnimation = findViewById(R.id.ConstraintLayout_searchAnimation);
        ImageView_advancedSearch = findViewById(R.id.ImageView_advancedSearch);
        ImageView_advancedSearch.setOnClickListener(this::openAdvancedSearchForm);

        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_saved) {
                displaySavedMovies();
            }

            if (item.getItemId() == R.id.nav_top250Movies) {
                displayTop250Movies();
            }
            return false;
        });

        EditText txtSearch = (searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(R.string.MainActivity_SearchMoviesHint);
        txtSearch.setHintTextColor(Color.WHITE);
        txtSearch.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // we call the api
                showLoadingAnimation();

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                requestManager.searchMovies(listener, query);
                CardView_search_placeholder.setVisibility(View.VISIBLE);
                ConstraintLayout_searchAnimation.setVisibility(View.VISIBLE);
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

    private final onSearchMoviesListener listener = new onSearchMoviesListener() {
        @Override
        public void onResponse(SearchResult result) {
            if(result == null || (result.getItems() != null && result.getItems().size() <= 0)){
                hideLoadingAnimation();
                Toast.makeText(MainActivity.this, R.string.HomePage_NoDataFound, Toast.LENGTH_LONG).show();
                return;
            }

            if(result.getErrorMessage() != null && !result.getErrorMessage().equals("")){
                hideLoadingAnimation();

                Toast.makeText(MainActivity.this, result.getErrorMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            if(result.getItems() == null){
                hideLoadingAnimation();
                Toast.makeText(MainActivity.this, R.string.Common_ApiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            showResult(result);
        }

        @Override
        public void onError(String error) {
            hideLoadingAnimation();
            Toast.makeText(MainActivity.this, R.string.Common_ApiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResult(SearchResult result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        adapter = new HomeRecyclerAdapter(this, result.getItems(), this);
        recyclerView.setAdapter(adapter);
        CardView_search_placeholder.setVisibility(View.GONE);
        ConstraintLayout_searchAnimation.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        toggleInputFields(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displaySavedMovies() {
        Intent intent = new Intent(MainActivity.this, SavedMoviesActivity.class);
        startActivity(intent);
    }

    private void displayTop250Movies() {
        Intent intent = new Intent(MainActivity.this, TopList.class);
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
        ImageView_advancedSearch.setEnabled(val);
    }

    ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == resultCode){
                    Intent i = result.getData();

                    if(i != null){
                        HashMap<String, Object> args = (HashMap<String, Object>) i.getSerializableExtra("data");

                        showLoadingAnimation();

                        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        requestManager.advancedSearchMovies(listener, args);
                        CardView_search_placeholder.setVisibility(View.VISIBLE);
                        ConstraintLayout_searchAnimation.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    }
                }
            });
}