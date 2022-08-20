package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.movielibrary.Adapters.HomeRecyclerAdapter;
import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Listeners.OnSearchMoviesListener;
import com.example.movielibrary.Models.SearchModels.SearchResult;
import com.example.movielibrary.Utils.RequestManager;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    SearchView searchView = null;
    RecyclerView recyclerView = null;
    HomeRecyclerAdapter adapter;
    RequestManager requestManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view_home);
        requestManager = new RequestManager(this);
        dialog = new ProgressDialog(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // we call the api
                dialog.setTitle("Searching...");
                dialog.show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                requestManager.searchMovies(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private final OnSearchMoviesListener listener = new OnSearchMoviesListener() {
        @Override
        public void onResponse(SearchResult result) {
            dialog.dismiss();
            if(result == null){
                Toast.makeText(MainActivity.this, "No data available!", Toast.LENGTH_LONG).show();
                return;
            }

            showResult(result);
        }

        @Override
        public void onError(String error) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "An error occured!", Toast.LENGTH_LONG).show();
        }
    };

    private void showResult(SearchResult result) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        adapter = new HomeRecyclerAdapter(this, result.getResults(), this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onMovieClicked(String id) {
        Toast.makeText(MainActivity.this, id, Toast.LENGTH_LONG).show();
    }
}