package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movielibrary.Adapters.MovieDetails.CastRecyclerAdapter;
import com.example.movielibrary.Adapters.MovieDetails.SimilarMoviesRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.BoxOffice;
import com.example.movielibrary.Models.SearchModels.DetailsSearch.DetailsMovieResponse;
import com.example.movielibrary.Models.SearchModels.TvSeriesInfo;
import com.example.movielibrary.MovieActivities.TopLists.BoxOfficeAllTime;
import com.example.movielibrary.MovieActivities.TopLists.Top250Movies;
import com.example.movielibrary.R;
import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.Shared.Helper;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.ImdbApi.RequestManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity implements OnMovieClickListener {

    TextView textView_movie_title,
             textView_movie_released,
             textView_movie_votes,
             textView_awards,
             textView_genres,
             textView_companies,
             textView_languages,
             textView_keywords,
             textView_movie_plot,
             textView_budget,
             textView_openingWeekendUSA,
             textView_grossUSA,
             textView_cumulativeWorldwideGross,
             textView_contentRating,
             textView_seasons,
             textView_creators;
    ImageView imageView_movie_poster;
    RecyclerView recyclerView_movie_cast,
                 recyclerView_similarMovies;
    CastRecyclerAdapter adapter;
    SimilarMoviesRecycleAdapter similarMoviesAdapter;
    ConstraintLayout constrainLayout_loadingWrapper;
    RequestManager requestManager;
    DBHandler dbHandler;
    ScrollView detailsPageContent;
    CardView cardView_faq, cardView_tvSeries;
    String title, poster, movieId , parent;
    Menu menu;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        this.menu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save){
            displaySaveMovieNotification();
            return true;
        }else if(item.getItemId() == R.id.action_delete){
            displayDeleteMovieNotification();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    public void displayDeleteMovieNotification(){
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
    }

    public void displaySaveMovieNotification(){
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
    }

    private void initViewElements(){
        dbHandler = new DBHandler(DetailsActivity.this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        constrainLayout_loadingWrapper = findViewById(R.id.constrainLayout_loadingWrapper);
        detailsPageContent = findViewById(R.id.detailsPageContent);
        textView_movie_title = findViewById(R.id.textView_movieName);
        textView_movie_released = findViewById(R.id.textView_movieReleased);
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
        textView_companies = findViewById(R.id.textView_companies);
        textView_contentRating = findViewById(R.id.textView_contentRating);
        textView_budget = findViewById(R.id.textView_budget);
        textView_openingWeekendUSA = findViewById(R.id.textView_openingWeekendUSA);
        textView_grossUSA = findViewById(R.id.textView_grossUSA);
        textView_cumulativeWorldwideGross = findViewById(R.id.textView_cumulativeWorldwideGross);
        textView_seasons = findViewById(R.id.textView_seasons);
        textView_creators = findViewById(R.id.textView_creators);
        cardView_tvSeries = findViewById(R.id.cardView_tvSeries);
        cardView_faq = findViewById(R.id.cardView_faq);
        cardView_faq.setOnClickListener(this::showFaqPage);
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
        String runtime = response.getRuntimeStr() != null ? response.getRuntimeStr() : getString(R.string.common_questionMark);

        textView_movie_title.setText(response.getTitle());
        textView_movie_released.setText(String.format("%s %s %s %s", getString(R.string.movieDetails_releaseDate), response.getReleaseDate(),
                                                        getString(R.string.movieDetails_runtime), runtime));

        String imdbVotes = response.getImDbRatingVotes();

        textView_movie_votes.setText(String.format("%s %s %s %s/%s", getString(R.string.movieDetails_votes), Helper.formatNumber(Double.parseDouble(imdbVotes)),
                                     getString(R.string.movieDetails_rating), response.getImDbRating(), "10"));

        String awards = response.getAwards();
        if(!Objects.equals(awards, "")){
            textView_awards.setText(awards);
        }else{
            textView_awards.setVisibility(View.GONE);
        }
        textView_genres.setText(response.getGenres());
        textView_movie_plot.setText(response.getPlot());
        textView_companies.setText(String.format("%s %s", getString(R.string.detailsActivity_companies), response.getCompanies()));
        textView_languages.setText(String.format("%s %s", getString(R.string.detailsActivity_languages), response.getLanguages()));
        textView_keywords.setText(String.format("%s %s", getString(R.string.detailsActivity_keywords), response.getKeywords()));
        textView_contentRating.setText(response.getContentRating());

        BoxOffice boxOffice = response.getBoxOffice();
        String budget = Objects.equals(boxOffice.getBudget(), "") ? getString(R.string.common_notAvailable): boxOffice.getBudget();
        String openingWeekendUSA = Objects.equals(boxOffice.getOpeningWeekendUSA(), "") ? getString(R.string.common_notAvailable): boxOffice.getOpeningWeekendUSA();
        String grossUSA = Objects.equals(boxOffice.getGrossUSA(), "") ? getString(R.string.common_notAvailable): boxOffice.getGrossUSA();
        String cumulativeWorldwideGross = Objects.equals(boxOffice.getCumulativeWorldwideGross(), "") ? getString(R.string.common_notAvailable): boxOffice.getCumulativeWorldwideGross();

        textView_budget.setText(String.format("%s %s", getString(R.string.detailsActivity_budget), budget));
        textView_openingWeekendUSA.setText(String.format("%s %s", getString(R.string.detailsActivity_openingWeekendUsa), openingWeekendUSA));
        textView_grossUSA.setText(String.format("%s %s", getString(R.string.detailsActivity_usaGross), grossUSA));
        textView_cumulativeWorldwideGross.setText(String.format("%s %s", getString(R.string.detailsActivity_cumulativeWorldwideGross), cumulativeWorldwideGross));

        title = response.getTitle();
        poster = response.getImage();
        movieId = response.getId();


        if(response.getTvSeriesInfo() != null){
            TvSeriesInfo tvSeriesInfo = response.getTvSeriesInfo();

            textView_creators.setText(String.format(getString(R.string.detailsActivity_tvSeriesCreators), tvSeriesInfo.getCreators()));
            textView_seasons.setText(String.format(getString(R.string.detailsActivity_tvSeriesSeasons), String.join(", ", tvSeriesInfo.getSeasons())));
        }else{
            cardView_tvSeries.setVisibility(View.GONE);
        }

        try {
            Picasso.get().load(response.getImage()).placeholder(R.drawable.loading).resize(Helper.getScreenWidth(), Helper.getScreenHeight()).into(imageView_movie_poster, new Callback() {
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
    }

    private void displayMovieDetails(DetailsMovieResponse response){
        if(!dbHandler.isMovieInDatabase(movieId)){

            menu.findItem(R.id.action_save).setVisible(true);
            menu.findItem(R.id.action_delete).setVisible(false);
        }else{
            menu.findItem(R.id.action_save).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(true);
        }

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

    private void showFaqPage(View view) {
        startActivity(new Intent(DetailsActivity.this, FaqActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, movieId).putExtra(MovieActivitiesDefaults.PARENT, DetailsActivity.class.toString()));
    }

    @Override
    public void onMovieClicked(String id) {
        startActivity(new Intent(DetailsActivity.this, DetailsActivity.class)
                .putExtra(MovieActivitiesDefaults.DATA, id).putExtra(MovieActivitiesDefaults.PARENT, DetailsActivity.class.toString()));
    }
}