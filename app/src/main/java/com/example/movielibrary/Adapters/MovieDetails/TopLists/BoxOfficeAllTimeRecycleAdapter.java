package com.example.movielibrary.Adapters.MovieDetails.TopLists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Listeners.OnMovieClickListener;
import com.example.movielibrary.Models.SearchModels.BoxOfficeAllTimeModel;
import com.example.movielibrary.R;

import java.util.List;

public class BoxOfficeAllTimeRecycleAdapter extends RecyclerView.Adapter<BoxOfficeViewHolder>{

        Context context;
        List<BoxOfficeAllTimeModel> list;
        OnMovieClickListener listener;

        public BoxOfficeAllTimeRecycleAdapter(Context context, List<BoxOfficeAllTimeModel> list, OnMovieClickListener listener) {
            this.context = context;
            this.list = list;
            this.listener = listener;
        }
    @NonNull
    @Override
    public BoxOfficeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxOfficeViewHolder(LayoutInflater.from(context).inflate(R.layout.box_office_all_time_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxOfficeViewHolder holder, int position) {

        holder.CardView_Container.setOnClickListener(view -> listener.onMovieClicked(list.get(position).getId()));

        holder.TextView_Rank.setText(String.format("#%s", list.get(position).getRank()));
        holder.TextView_WorldwideLifetimeGross.setText(list.get(position).getWorldwideLifetimeGross());
        holder.TextView_DomesticLifetimeGross.setText(list.get(position).getDomesticLifetimeGross());
        holder.TextView_ForeignLifetimeGross.setText(list.get(position).getForeignLifetimeGross());
        holder.TextView_Foreign.setText(list.get(position).getForeign());
        holder.TextView_Domestic.setText(list.get(position).getDomestic());
        holder.TextView_Title.setText(String.format("%s (%s)", list.get(position).getTitle(), list.get(position).getYear()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BoxOfficeViewHolder extends RecyclerView.ViewHolder {

    TextView TextView_Title,
             TextView_Rank,
             TextView_WorldwideLifetimeGrossTitle,
             TextView_WorldwideLifetimeGross,
             TextView_DomesticLifetimeGrossTitle,
             TextView_DomesticLifetimeGross,
             TextView_ForeignLifetimeGrossTitle,
             TextView_ForeignLifetimeGross,
             TextView_Foreign,
             TextView_Domestic;

    CardView CardView_Container;

    public BoxOfficeViewHolder(@NonNull View itemView) {
        super(itemView);

        TextView_Title = itemView.findViewById(R.id.TextView_Title);
        TextView_Rank = itemView.findViewById(R.id.TextView_Rank);
        TextView_WorldwideLifetimeGrossTitle = itemView.findViewById(R.id.TextView_WorldwideLifetimeGrossTitle);
        TextView_WorldwideLifetimeGross = itemView.findViewById(R.id.TextView_WorldwideLifetimeGross);
        TextView_DomesticLifetimeGrossTitle = itemView.findViewById(R.id.TextView_DomesticLifetimeGrossTitle);
        TextView_DomesticLifetimeGross = itemView.findViewById(R.id.TextView_DomesticLifetimeGross);
        TextView_ForeignLifetimeGrossTitle = itemView.findViewById(R.id.TextView_ForeignLifetimeGrossTitle);
        TextView_ForeignLifetimeGross = itemView.findViewById(R.id.TextView_ForeignLifetimeGross);
        TextView_Foreign = itemView.findViewById(R.id.TextView_Foreign);
        TextView_Domestic = itemView.findViewById(R.id.TextView_Domestic);
        CardView_Container = itemView.findViewById(R.id.CardView_Container);
    }
}
