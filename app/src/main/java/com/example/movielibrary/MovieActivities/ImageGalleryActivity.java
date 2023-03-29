package com.example.movielibrary.MovieActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movielibrary.Adapters.ImageGalleryRecycleAdapter;
import com.example.movielibrary.Listeners.OnMovieResponseListener;
import com.example.movielibrary.Models.Images.ImagesResponseModel;

import com.example.movielibrary.R;
import com.example.movielibrary.Shared.Helper;
import com.example.movielibrary.Shared.MovieActivitiesDefaults;
import com.example.movielibrary.Utils.RequestManager;

import java.util.Objects;

public class ImageGalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView_imageGallery;
    private String itemId, parent;
    private RequestManager requestManager;
    private ImageGalleryRecycleAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_galery);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        recyclerView_imageGallery = findViewById(R.id.recyclerView_imageGallery);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            itemId = extras.getString(MovieActivitiesDefaults.ID);
            parent = extras.getString(MovieActivitiesDefaults.PARENT);
        }

        requestManager = new RequestManager(ImageGalleryActivity.this);

        requestManager.getImages(_listener, itemId);
    }

    private final OnMovieResponseListener<ImagesResponseModel> _listener = new OnMovieResponseListener<ImagesResponseModel>() {
        @Override
        public void onResponse(ImagesResponseModel response) {

            if (response == null) {
                Toast.makeText(ImageGalleryActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
                return;
            }

            if(response.getErrorMessage() != null && !Objects.equals(response.getErrorMessage(), "")){
                Intent intent = new Intent(ImageGalleryActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(ImageGalleryActivity.this, response.getErrorMessage(), Toast.LENGTH_LONG).show();
                startActivity(intent);
                return;
            }

            showResults(response);
        }

        @Override
        public void onError(String message) {

            Toast.makeText(ImageGalleryActivity.this, R.string.common_apiErrorResponse, Toast.LENGTH_LONG).show();
        }
    };

    private void showResults(ImagesResponseModel response) {
        recycleAdapter = new ImageGalleryRecycleAdapter(ImageGalleryActivity.this, response.getItems());

        recyclerView_imageGallery.setHasFixedSize(true);
        recyclerView_imageGallery.setLayoutManager(new GridLayoutManager(ImageGalleryActivity.this, Helper.getGridItemsCount()));
        recyclerView_imageGallery.setAdapter(recycleAdapter);
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

        if (Objects.equals(parent, DetailsActivity.class.toString())) {
            i = new Intent(this, DetailsActivity.class);
            i.putExtra(MovieActivitiesDefaults.DATA, itemId);
        }
        else {
            i = new Intent(this, MainActivity.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return i;
    }
}