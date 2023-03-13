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
import com.example.movielibrary.Models.SearchModels.TopLists.BoxOfficeModel;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BoxOfficeRecycleAdapter extends RecyclerView.Adapter<BoxOfficeViewHolder> {

    Context context;
    List<BoxOfficeModel> list;
    OnMovieClickListener listener;

    public BoxOfficeRecycleAdapter(Context context, List<BoxOfficeModel> list, OnMovieClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BoxOfficeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxOfficeViewHolder(LayoutInflater.from(context).inflate(R.layout.box_office_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxOfficeViewHolder holder, int position) {

        holder.CardView_Container.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        holder.TextView_Rank.setText(String.format("#%s", list.get(position).getRank()));
        holder.TextView_Title.setText(list.get(position).getTitle());
        holder.TextView_Gross.setText(list.get(position).getGross());
        holder.TextView_Weekend.setText(list.get(position).getWeekend());
        holder.TextView_Weeks.setText(list.get(position).getWeeks());

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

class BoxOfficeViewHolder extends RecyclerView.ViewHolder {

    TextView TextView_Title,
            TextView_Rank,
            TextView_Weekend,
            TextView_Weeks,
            TextView_Gross;

    ImageView ImageView_Poster;

    CardView CardView_Container;

    public BoxOfficeViewHolder(@NonNull View itemView) {
        super(itemView);

        TextView_Title = itemView.findViewById(R.id.textView_comingSoonTitle);
        TextView_Rank = itemView.findViewById(R.id.TextView_Rank);

        TextView_Weekend = itemView.findViewById(R.id.TextView_Weekend);
        TextView_Weeks = itemView.findViewById(R.id.TextView_Weeks);
        TextView_Gross = itemView.findViewById(R.id.TextView_Gross);
        ImageView_Poster = itemView.findViewById(R.id.imageView_poster);

        CardView_Container = itemView.findViewById(R.id.cardView_container);
    }
}
