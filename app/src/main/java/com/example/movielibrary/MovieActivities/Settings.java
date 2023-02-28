package com.example.movielibrary.MovieActivities;

import static com.example.movielibrary.Shared.Settings.IMDB_API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.movielibrary.Database.DBHandler;
import com.example.movielibrary.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    DBHandler db = null;
    TextInputEditText TextInputEditText_ImdbApiKey;
    Button Button_SaveSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        db = new DBHandler(Settings.this);

        String imdbApiKey = db.getSettingByName(IMDB_API_KEY);

        TextInputEditText_ImdbApiKey = findViewById(R.id.TextInputEditText_ImdbApiKey);
        Button_SaveSettings = findViewById(R.id.Button_SaveSettings);

        Button_SaveSettings.setOnClickListener(view -> {
            String newImdbApiKey = String.valueOf(TextInputEditText_ImdbApiKey.getText());
            if(!newImdbApiKey.equals("")){
                db.insertSetting(IMDB_API_KEY, newImdbApiKey);
                Toast.makeText(Settings.this, "Imdb key successfully updated", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(Settings.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            }

        });
        if(imdbApiKey != null){
            TextInputEditText_ImdbApiKey.setText(imdbApiKey);
        }

    }
}