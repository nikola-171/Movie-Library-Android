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
import com.example.movielibrary.Models.SearchModels.TopListMovieModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TopMoviesListRecycleAdapter extends RecyclerView.Adapter<TopMoviesListViewHolder>{

    Context context;
    List<TopListMovieModel> list;
    OnMovieClickListener listener;

    public TopMoviesListRecycleAdapter(Context context, List<TopListMovieModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopMoviesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopMoviesListViewHolder(LayoutInflater.from(context).inflate(R.layout.top_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopMoviesListViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));

        holder.textView_movie.setText(list.get(position).getTitle());

        String crew = list.get(position).getCrew();

        if(crew != null && !crew.equals("")){
            holder.TextView_Crew.setText(crew);
        }else{
            holder.TextView_Crew.setVisibility(View.GONE);
        }

        String ratingCount = list.get(position).getImDbRatingCount();

        if(ratingCount != null && !ratingCount.equals("")){
            holder.TextView_Votes.setText(String.format("%s votes", formatter.format(Double.parseDouble(ratingCount))));
        }

        String rating = list.get(position).getImDbRating();

        if(rating != null && !rating.equals("")){
            holder.TextView_Rating.setText(String.format("%s/10", rating));
        }else{
            holder.TextView_Rating.setVisibility(View.GONE);
        }

        String rank = list.get(position).getRank();

        if(rank != null && !rank.equals("")){
            holder.TextView_Place.setText(String.format("#%s", rank));
        }

        if(list.get(position).getImage() != null && !Objects.equals(list.get(position).getImage(), "")){
            Picasso.get().load(list.get(position).getImage()).fit().into(holder.imageView_poster);
        }
        holder.homeContainer.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        holder.homeContainer.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class TopMoviesListViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_poster;
    TextView textView_movie, TextView_Place, TextView_Crew, TextView_Rating, TextView_Votes;
    CardView homeContainer;

    public TopMoviesListViewHolder(@NonNull View itemView) {
        super(itemView);
        TextView_Rating = itemView.findViewById(R.id.TextView_Rating);
        TextView_Crew = itemView.findViewById(R.id.TextView_Crew);
        imageView_poster = itemView.findViewById(R.id.imageView_poster);
        textView_movie = itemView.findViewById(R.id.textView_movie);
        homeContainer = itemView.findViewById(R.id.home_container);
        TextView_Place = itemView.findViewById(R.id.TextView_Place);
        TextView_Votes = itemView.findViewById(R.id.TextView_Votes);
        TextView_Votes.setSelected(true);
        textView_movie.setSelected(true);
        TextView_Crew.setSelected(true);
    }
}
