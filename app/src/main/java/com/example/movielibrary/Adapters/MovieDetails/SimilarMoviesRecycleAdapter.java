package com.example.movielibrary.Adapters.MovieDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Models.SearchModels.SimilarMovieModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SimilarMoviesRecycleAdapter extends RecyclerView.Adapter<SimilarMoviesViewHolder> {

    Context context;
    List<SimilarMovieModel> list;
    OnMovieClickListener listener;

    public SimilarMoviesRecycleAdapter(Context context, List<SimilarMovieModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SimilarMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SimilarMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.similar_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarMoviesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_rating.setText(String.format("%s/10", list.get(position).getImDbRating()));

        try {
            Picasso.get().load(list.get(position).getImage()).resize(500, 600).placeholder(R.drawable.loading_anim_300x400).into(holder.imageView_poster);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.CardView_wrapper.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

class SimilarMoviesViewHolder extends RecyclerView.ViewHolder {

    TextView textView_title, textView_rating;
    ImageView imageView_poster;
    CardView CardView_wrapper;

    public SimilarMoviesViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_title = itemView.findViewById(R.id.textView_title);
        textView_rating = itemView.findViewById(R.id.textView_rating);
        imageView_poster = itemView.findViewById(R.id.imageView_image);
        CardView_wrapper = itemView.findViewById(R.id.CardView_wrapper);

        textView_title.setSelected(true);

    }
}