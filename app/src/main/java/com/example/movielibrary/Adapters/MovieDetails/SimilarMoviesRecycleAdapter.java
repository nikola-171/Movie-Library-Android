package com.example.movielibrary.Adapters.MovieDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movielibrary.Models.SearchModels.SimilarMovieModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarMoviesRecycleAdapter extends RecyclerView.Adapter<SimilarMoviesViewHolder> {

    Context context;
    List<SimilarMovieModel> list;

    public SimilarMoviesRecycleAdapter(Context context, List<SimilarMovieModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMoviesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_rating.setText(list.get(position).getImDbRating() + "/10");

        try {
            Picasso.get().load(list.get(position).getImage()).resize(300, 400).into(holder.imageView_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SimilarMoviesViewHolder extends RecyclerView.ViewHolder {

    TextView textView_title, textView_rating;
    ImageView imageView_poster;

    public SimilarMoviesViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_title = itemView.findViewById(R.id.textView_title);
        textView_rating = itemView.findViewById(R.id.textView_rating);
        imageView_poster = itemView.findViewById(R.id.imageView_poster);

    }
}