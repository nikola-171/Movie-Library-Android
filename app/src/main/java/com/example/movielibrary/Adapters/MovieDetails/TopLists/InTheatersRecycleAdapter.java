package com.example.movielibrary.Adapters.MovieDetails.TopLists;

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
import com.example.movielibrary.Models.SearchModels.TopLists.InTheatersModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class InTheatersRecycleAdapter extends RecyclerView.Adapter<InTheatersViewHolder>{

    Context context;
    List<InTheatersModel> list;
    OnMovieClickListener listener;

    public InTheatersRecycleAdapter(Context context, List<InTheatersModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InTheatersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InTheatersViewHolder(LayoutInflater.from(context).inflate(R.layout.in_theater_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InTheatersViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));

        holder.textView_movie.setText(list.get(position).getFullTitle());

        String crew = list.get(position).getStars();

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

        String rank = list.get(position).getContentRating();

        if(rank != null && !rank.equals("")){
            holder.TextView_Place.setText(rank);
        }

        holder.TextView_ReleaseState.setText(String.format("Released on: %s \n %s", list.get(position).getReleaseState() ,list.get(position).getRuntimeStr()));
        holder.TextView_Genres.setText(list.get(position).getGenres());
        holder.TextView_Plot.setText(list.get(position).getPlot());

        holder.TextView_MetaCritic.setText(String.format("Metacritic rating: %s", list.get(position).getMetacriticRating()));

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

class InTheatersViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_poster;
    TextView textView_movie, TextView_Place, TextView_Crew, TextView_Rating, TextView_Votes, TextView_Plot,
             TextView_Genres, TextView_ReleaseState, TextView_MetaCritic;
    CardView homeContainer;

    public InTheatersViewHolder(@NonNull View itemView) {
        super(itemView);
        TextView_Rating = itemView.findViewById(R.id.TextView_Rating);
        TextView_Crew = itemView.findViewById(R.id.TextView_Crew);
        imageView_poster = itemView.findViewById(R.id.imageView_image);
        textView_movie = itemView.findViewById(R.id.TextView_Movie);
        homeContainer = itemView.findViewById(R.id.cardView_home_container);
        TextView_Place = itemView.findViewById(R.id.TextView_Place);
        TextView_Votes = itemView.findViewById(R.id.TextView_Votes);
        TextView_Plot = itemView.findViewById(R.id.TextView_Plot);
        TextView_Genres = itemView.findViewById(R.id.textView_comingSoonGenres);
        TextView_ReleaseState = itemView.findViewById(R.id.textView_releaseState);
        TextView_MetaCritic = itemView.findViewById(R.id.TextView_MetaCritic);
        TextView_Votes.setSelected(true);
        textView_movie.setSelected(true);
        TextView_Crew.setSelected(true);
    }
}
