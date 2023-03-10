package com.example.movielibrary.Adapters.MovieDetails.TopLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Models.SearchModels.TopLists.MostPopularMoviesModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MostPopularMoviesRecycleAdapter extends RecyclerView.Adapter<MostPopularMoviesViewHolder>{

    Context context;
    List<MostPopularMoviesModel> list;
    OnMovieClickListener listener;

    public MostPopularMoviesRecycleAdapter(Context context, List<MostPopularMoviesModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MostPopularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MostPopularMoviesViewHolder(LayoutInflater.from(context).inflate(R.layout.most_popular_movies_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularMoviesViewHolder holder, int position) {
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

        holder.TextView_Rating.setText(String.format("%s/10", rating != null && !rating.equals("") ? rating : "?"));

        String rank = list.get(position).getRank();

        if(rank != null && !rank.equals("")){
            holder.TextView_Place.setText(String.format("#%s", rank));
        }

        if(list.get(position).getImage() != null && !Objects.equals(list.get(position).getImage(), "")){
            Picasso.get().load(list.get(position).getImage()).fit().into(holder.imageView_poster);
        }
        holder.homeContainer.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        holder.homeContainer.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        String rankUpDown = list.get(position).getRankUpDown();

        holder.TextView_RankChange.setText(rankUpDown);
        if(rankUpDown.contains("-")){
            holder.TextView_RankChange.setTextColor(ContextCompat.getColor(context, R.color.red));
        }else if(rankUpDown.contains("+")){
            holder.TextView_RankChange.setTextColor(ContextCompat.getColor(context, R.color.green));

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class MostPopularMoviesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_poster;
    TextView textView_movie, TextView_Place, TextView_Crew, TextView_Rating, TextView_Votes, TextView_Plot,
            TextView_Genres, TextView_ReleaseState, TextView_MetaCritic, TextView_RankChange;
    CardView homeContainer;

    public MostPopularMoviesViewHolder(@NonNull View itemView) {
        super(itemView);
        TextView_Rating = itemView.findViewById(R.id.TextView_Rating);
        TextView_Crew = itemView.findViewById(R.id.TextView_Crew);
        imageView_poster = itemView.findViewById(R.id.ImageView_Image);
        textView_movie = itemView.findViewById(R.id.TextView_Movie);
        homeContainer = itemView.findViewById(R.id.CardView_Home_container);
        TextView_Place = itemView.findViewById(R.id.TextView_Place);
        TextView_Votes = itemView.findViewById(R.id.TextView_Votes);
        TextView_Plot = itemView.findViewById(R.id.TextView_Plot);
        TextView_Genres = itemView.findViewById(R.id.TextView_Genres);
        TextView_ReleaseState = itemView.findViewById(R.id.TextView_ReleaseState);
        TextView_MetaCritic = itemView.findViewById(R.id.TextView_MetaCritic);
        TextView_RankChange = itemView.findViewById(R.id.TextView_RankChange);
        TextView_Votes.setSelected(true);
        textView_movie.setSelected(true);
        TextView_Crew.setSelected(true);
    }
}