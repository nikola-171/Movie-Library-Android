package com.example.movielibrary.Adapters.MovieDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Models.SearchModels.DetailsSearch.Actor;
import com.example.movielibrary.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CastRecyclerAdapter extends RecyclerView.Adapter<CastViewHolder>{

    Context context;
    List<Actor> list;

    public CastRecyclerAdapter(Context context, List<Actor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        holder.textView_actor.setText(list.get(position).getName());
        holder.textView_character.setText(list.get(position).getAsCharacter());

        try {
            Picasso.get().load(list.get(position).getImage()).resize(300, 400).into(holder.imageView_actor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class CastViewHolder extends RecyclerView.ViewHolder {

    TextView textView_actor, textView_character;
    ImageView imageView_actor;

    public CastViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_actor = itemView.findViewById(R.id.textView_actor);
        textView_character = itemView.findViewById(R.id.textView_character);
        imageView_actor = itemView.findViewById(R.id.imageView_actor);

    }
}

