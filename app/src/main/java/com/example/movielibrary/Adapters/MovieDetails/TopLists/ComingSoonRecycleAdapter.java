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
import com.example.movielibrary.Models.SearchModels.TopLists.ComingSoonModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComingSoonRecycleAdapter extends RecyclerView.Adapter<ComingSoonViewHolder>{

    Context context;
    List<ComingSoonModel> list;
    OnMovieClickListener listener;

    public ComingSoonRecycleAdapter(Context context, List<ComingSoonModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ComingSoonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComingSoonViewHolder(LayoutInflater.from(context).inflate(R.layout.coming_soon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComingSoonViewHolder holder, int position) {

        holder.CardView_Container.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        holder.TextView_Title.setText(list.get(position).getTitle());
        holder.TextView_ReleaseState.setText(list.get(position).getReleaseState());
        holder.TextView_Genres.setText(list.get(position).getGenres());
        holder.TextView_Stars.setText(list.get(position).getStars());

        try {
            Picasso.get().load(list.get(position).getImage()).into(holder.ImageView_Poster);
        } catch (Exception ignored) {
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class ComingSoonViewHolder extends RecyclerView.ViewHolder {

    TextView TextView_Title,
            TextView_ReleaseState,
            TextView_Genres,
            TextView_Stars;

    CardView CardView_Container;

    ImageView ImageView_Poster;

    public ComingSoonViewHolder(@NonNull View itemView) {
        super(itemView);

        ImageView_Poster = itemView.findViewById(R.id.ImageView_Poster);
        TextView_Title = itemView.findViewById(R.id.TextView_Title);
        TextView_ReleaseState = itemView.findViewById(R.id.TextView_ReleaseState);
        TextView_Genres = itemView.findViewById(R.id.TextView_Genres);
        TextView_Stars = itemView.findViewById(R.id.TextView_Stars);
        CardView_Container = itemView.findViewById(R.id.CardView_Container);

    }
}
