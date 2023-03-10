package com.example.movielibrary.MovieActivities;

import static com.example.movielibrary.R.*;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.genres;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.genresTags;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.genresValues;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.keywordList;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.locations;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.movieMeter;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.numVotes;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.plot;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.ratingLimit;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.resultCode;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.title;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.titleFeature;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.titleTypeTags;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.titleTypeVales;
import static com.example.movielibrary.Shared.MovieActivitiesAdvancedSearchDefaults.userRating;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.movielibrary.Filters.DecimalDigitsInputFilter;
import com.example.movielibrary.Dialogs.MultiChoiceDialogFragment;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AdvancedFilterForm extends AppCompatActivity implements MultiChoiceDialogFragment.onMultiChoiceListener,
        DatePickerDialog.OnDateSetListener{

    HashMap<String, Object> filterData = new HashMap<>();

    Button Button_Submit, Button_Cancel;

    TextInputEditText textInputEditText_TitleType, TextInputEditText_Genres, TextInputEditText_RatingMax, TextInputEditText_RatingMin,
                      TextInputEditText_Title, textInputEditText_Keywords,
                      TextInputEditText_VotesMax, TextInputEditText_VotesMin, textInputEditText_Plot,
                      TextInputEditText_RuntimeMin, TextInputEditText_RuntimeMax, textInputEditText_FilmingLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_advanced_filter_form);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        Button_Submit = findViewById(id.Button_Submit);
        Button_Cancel = findViewById(R.id.Button_Cancel);
        Button_Cancel.setOnClickListener(this::cancelSearch);

        TextInputEditText_Title = findViewById(id.TextInputEditText_Title);
        textInputEditText_Keywords = findViewById(R.id.textInputEditText_Keywords);
        TextInputEditText_VotesMax = findViewById(R.id.TextInputEditText_VotesMax);
        TextInputEditText_VotesMin = findViewById(R.id.TextInputEditText_VotesMin);
        textInputEditText_Plot = findViewById(R.id.textInputEditText_Plot);
        TextInputEditText_RuntimeMin = findViewById(R.id.TextInputEditText_RuntimeMin);
        TextInputEditText_RuntimeMax = findViewById(R.id.TextInputEditText_RuntimeMax);
        textInputEditText_FilmingLocation = findViewById(R.id.textInputEditText_FilmingLocation);

        Button_Submit.setOnClickListener(view -> {

            if(!validateForm()){
                return;
            }

            addRuntimeValues();

            addKeywordValues();

            addFilmingLocation();

            addUserRating();

            addUserVotes();

            addTitle();

            addPlot();

            setResult(resultCode, createFilterDataIntent());

            finish();
        });

        textInputEditText_TitleType = findViewById(id.textInputEditText_TitleType);
        TextInputEditText_Genres = findViewById(id.TextInputEditText_Genres);

        TextInputEditText_RatingMax = findViewById(id.TextInputEditText_RatingMax);
        TextInputEditText_RatingMin = findViewById(id.TextInputEditText_RatingMin);

        TextInputEditText_RatingMin.setFilters(new InputFilter[] {new DecimalDigitsInputFilter()});
        TextInputEditText_RatingMax.setFilters(new InputFilter[] {new DecimalDigitsInputFilter()});

        textInputEditText_TitleType.setOnClickListener(view -> {

            DialogFragment dialog = new MultiChoiceDialogFragment(titleTypeTags, titleTypeVales, textInputEditText_TitleType, getString(string.advancedFilterForm_dialogTitleType), titleFeature);

            dialog.show(getSupportFragmentManager(), MovieActivitiesDefaults.DIALOG_TAG);
        });

        TextInputEditText_Genres.setOnClickListener(view -> {
            DialogFragment dialog = new MultiChoiceDialogFragment(genresTags, genresValues, TextInputEditText_Genres, getString(string.advancedFilterForm_genresDialogTitle), genres);

            dialog.show(getSupportFragmentManager(), MovieActivitiesDefaults.DIALOG_TAG);
        });
    }

    private Intent createFilterDataIntent(){
        Intent i = new Intent();

        i.putExtra(MovieActivitiesDefaults.DATA, filterData);

        return i;
    }

    private void addTitle(){
        if(!Objects.requireNonNull(TextInputEditText_Title.getText()).toString().trim().equals("")){
            filterData.put(title, TextInputEditText_Title.getText().toString().trim());
        }
    }

    private void addPlot(){
        if(!Objects.requireNonNull(textInputEditText_Plot.getText()).toString().trim().equals("")){
            filterData.put(plot, textInputEditText_Plot.getText().toString().trim());
        }
    }

    private void cancelSearch(View view){
        Intent i = new Intent(AdvancedFilterForm.this, MainActivity.class);
        startActivity(i);
    }

    private void addUserRating(){
        StringBuilder b = new StringBuilder();

        String minValue = String.valueOf(TextInputEditText_RatingMin.getText());
        String maxValue = String.valueOf(TextInputEditText_RatingMax.getText());

        if(!minValue.equals("")){
            b.append(minValue);
        }

        if(!maxValue.equals("")){
            if(!minValue.equals("")){
                b.append(",");
            }

            b.append(maxValue);
        }

        if(!minValue.equals("") || !maxValue.equals("")){
            filterData.put(userRating, b.toString());
        }
    }

    private void addUserVotes(){
        StringBuilder votes = new StringBuilder();

        String minVotesValue = String.valueOf(TextInputEditText_VotesMin.getText());
        String maxVotesValue = String.valueOf(TextInputEditText_VotesMax.getText());

        if(!minVotesValue.equals("")){
            votes.append(minVotesValue).append(",");
        }

        if(!maxVotesValue.equals("")){
            votes.append(maxVotesValue);
        }

        if(!minVotesValue.equals("") || !maxVotesValue.equals("")){
            filterData.put(numVotes, votes.toString());
        }
    }

    private void addFilmingLocation(){
        String filmingLocation = Objects.requireNonNull(textInputEditText_FilmingLocation.getText()).toString();

        if(!filmingLocation.equals("")){
            filterData.put(locations, filmingLocation);
        }
    }

    private void addKeywordValues(){
        String keywords = Objects.requireNonNull(textInputEditText_Keywords.getText()).toString();

        if(!keywords.equals("")){
            filterData.put(keywordList, keywords);
        }
    }

    private void addRuntimeValues(){
        StringBuilder votes = new StringBuilder();

        String minValue = String.valueOf(TextInputEditText_RuntimeMin.getText());
        String maxValue = String.valueOf(TextInputEditText_RuntimeMax.getText());

        if(!minValue.equals("")){
            votes.append(minValue).append(",");
        }

        if(!maxValue.equals("")){
            votes.append(maxValue);
        }

        if(!minValue.equals("") || !maxValue.equals("")){
            filterData.put(movieMeter, votes.toString());
        }
    }

    @Override
    public void onPositiveButtonClicked(Pair<String, ArrayList<String>> data) {

        filterData.remove(data.first);

        filterData.put(data.first, String.join(",",data.second));
    }

    private boolean validateForm(){

        String minValue = String.valueOf(TextInputEditText_RatingMin.getText());
        String maxValue = String.valueOf(TextInputEditText_RatingMax.getText());

        String minVotesValue = String.valueOf(TextInputEditText_VotesMin.getText());
        String maxVotesValue = String.valueOf(TextInputEditText_VotesMax.getText());

        String minRuntimeValue = String.valueOf(TextInputEditText_RuntimeMin.getText());
        String maxRuntimeValue = String.valueOf(TextInputEditText_RuntimeMax.getText());

        if(!minValue.equals("") && Float.parseFloat(minValue) > ratingLimit){
            Toast.makeText(AdvancedFilterForm.this, R.string.common_validation_ratingMinValue, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!maxValue.equals("") && Float.parseFloat(maxValue) > ratingLimit){
            Toast.makeText(AdvancedFilterForm.this, R.string.common_validation_ratingMaxValue, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!minValue.equals("") && !maxValue.equals("") && Float.parseFloat(minValue) > Float.parseFloat( maxValue)){
            Toast.makeText(AdvancedFilterForm.this, R.string.common_validation_ratingMinGreaterThanMax, Toast.LENGTH_SHORT).show();

            return false;
        }

        if(!minVotesValue.equals("") && !maxVotesValue.equals("") && Float.parseFloat(minVotesValue) > Float.parseFloat(maxVotesValue)){
            Toast.makeText(AdvancedFilterForm.this, R.string.common_validation_votesMinGreaterThanMax, Toast.LENGTH_SHORT).show();

            return false;
        }

        if(!minRuntimeValue.equals("") && !maxRuntimeValue.equals("") && Float.parseFloat(minRuntimeValue) > Float.parseFloat(maxRuntimeValue)){
            Toast.makeText(AdvancedFilterForm.this, R.string.common_validation_runtimeMinGreaterThanMax, Toast.LENGTH_SHORT).show();

            return false;
        }

        return true;

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}