package com.example.movielibrary.MovieActivities;

import static com.example.movielibrary.R.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Pair;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
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

    TextView  TextView_releaseDateTo, TextView_releaseDateFrom;

    String[] titleTypeTags = {"Feature Film", "TV Movie", "TV Series", "TV Episode", "TV Special", "Mini-Series",
                              "Documentary", "Short Film", "Video", "TV Short", "Podcast Series",
                              "Podcast Episode"};

    String[] titleTypeVales = {"feature", "tv_movie", "tv_series", "tv_episode", "tv_special", "tv_miniseries",
            "documentary", "short", "video", "tv_short", "podcast_series",
            "podcast_episode",};

    String[] genresTags = {"Action", "Adventure" , "Animation", "Biography", "Comedy",
         "Crime", "Documentary", "Drama", "Family", "Fantasy", "Film-Noir", "Game-Show",
         "History", "Horror", "Music", "Musical", "Mystery", "News", "Reality-TV","Romance",
            "Sci-Fi", "Sport", "Talk-Show", "Thriller", "War", "Western"
    };

    String[] genresValues = {"action", "adventure" , "animation", "biography", "comedy",
            "crime", "documentary", "drama", "family", "fantasy", "film_noir", "game_show",
            "history", "horror", "music", "musical", "mystery", "news", "reality_tV","romance",
            "sci-fi", "sport", "talk_show", "thriller", "war", "western"
    };

    HashMap<String, Object> filterData = new HashMap<String, Object>();

    ImageView ImageView_Search;

    Button Button_Submit;

    TextInputEditText textInputEditText_TitleType, TextInputEditText_Genres, TextInputEditText_RatingMax, TextInputEditText_RatingMin,
                      TextInputEditText_Title, textInputEditText_Keywords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_advanced_filter_form);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);

        Button_Submit = findViewById(id.Button_Submit);
        TextInputEditText_Title = findViewById(id.TextInputEditText_Title);
        textInputEditText_Keywords = findViewById(R.id.textInputEditText_Keywords);

        Button_Submit.setOnClickListener(view -> {

            if(!ValidateForm()){
                return;
            }

            String keywords = Objects.requireNonNull(textInputEditText_Keywords.getText()).toString();

            if(!keywords.equals("")){
                filterData.put("keywords", keywords);
            }

            StringBuilder b = new StringBuilder();

            String minValue = String.valueOf(TextInputEditText_RatingMin.getText());
            String maxValue = String.valueOf(TextInputEditText_RatingMax.getText());

            if(!minValue.equals("")){
                b.append(minValue);
            }

            if(!maxValue.equals("")){
                if(!minValue.equals("null")){
                    b.append(",");
                }

                b.append(maxValue);
            }

            if(!minValue.equals("null") || !maxValue.equals("null")){
                filterData.put("user_rating", b.toString());
            }

            if(!TextInputEditText_Title.getText().equals("")){
                filterData.put("title", TextInputEditText_Title.getText().toString().trim());
            }

            Intent i = new Intent();

            i.putExtra("data", filterData);

            setResult(0, i);

            finish();
        });

        textInputEditText_TitleType = findViewById(id.textInputEditText_TitleType);
        TextInputEditText_Genres = findViewById(id.TextInputEditText_Genres);

        TextInputEditText_RatingMax = findViewById(id.TextInputEditText_RatingMax);
        TextInputEditText_RatingMin = findViewById(id.TextInputEditText_RatingMin);

        TextInputEditText_RatingMin.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,2)});
        TextInputEditText_RatingMax.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,2)});

        textInputEditText_TitleType.setOnClickListener(view -> {

            DialogFragment dialog = new MultiChoiceDialogFragment(titleTypeTags,titleTypeVales, textInputEditText_TitleType, "Dialog Title Type", "title_feature");

            dialog.show(getSupportFragmentManager(), MovieActivitiesDefaults.DIALOG_TAG);
        });

        TextInputEditText_Genres.setOnClickListener(view -> {
            DialogFragment dialog = new MultiChoiceDialogFragment(genresTags,genresValues, TextInputEditText_Genres, "Genre(s)", "genres");

            dialog.show(getSupportFragmentManager(), MovieActivitiesDefaults.DIALOG_TAG);
        });
    }

    @Override
    public void onPositiveButtonClicked(Pair<String, ArrayList<String>> data) {

        filterData.remove(data.first);

        filterData.put(data.first, String.join(",",data.second));
    }

    private boolean ValidateForm(){

        String minValue = String.valueOf(TextInputEditText_RatingMin.getText());
        String maxValue = String.valueOf(TextInputEditText_RatingMax.getText());

        if(!minValue.equals("") && Float.parseFloat(minValue) >= 10){
            Toast.makeText(AdvancedFilterForm.this, "Value must not be grater that 10", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!maxValue.equals("") && Float.parseFloat( maxValue) >= 10){
            Toast.makeText(AdvancedFilterForm.this, "Value must not be grater that 10", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!minValue.equals("") && !maxValue.equals("") && Float.parseFloat(minValue) > Float.parseFloat( maxValue)){
            Toast.makeText(AdvancedFilterForm.this, "Minimum rating must not be greater that maximum rating", Toast.LENGTH_SHORT).show();

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