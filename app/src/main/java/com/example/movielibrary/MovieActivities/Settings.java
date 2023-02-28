package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.movielibrary.R;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    }
}