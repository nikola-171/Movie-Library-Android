package com.example.movielibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Models.UserRatings.AllRatingsItem;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.Helper;

import java.util.List;

public class UserRatingRecycleAdapter extends RecyclerView.Adapter<UserRatingViewHolder> {
    private final Context context;
    private final List<AllRatingsItem> list;

    public UserRatingRecycleAdapter(Context context, List<AllRatingsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserRatingViewHolder(LayoutInflater.from(context).inflate(R.layout.user_rating_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserRatingViewHolder holder, int position) {

        AllRatingsItem currentItem = list.get(position);

        try {
            Double i = Double.parseDouble(currentItem.getVotes());
            holder.textView_userRatingVotes.setText(Helper.formatNumber(i));
        }catch (Exception e) {
            holder.textView_userRatingVotes.setText(currentItem.getVotes());
        }

        holder.textView_userRatingStars.setText(currentItem.getRating());
        holder.textView_userRatingPercentage.setText(currentItem.getPercent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class UserRatingViewHolder extends RecyclerView.ViewHolder {

    TextView textView_userRatingStars, textView_userRatingPercentage,
            textView_userRatingVotes;

    public UserRatingViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_userRatingStars = itemView.findViewById(R.id.textView_userRatingStars);
        textView_userRatingPercentage = itemView.findViewById(R.id.textView_userRatingPercentage);
        textView_userRatingVotes = itemView.findViewById(R.id.textView_userRatingVotes);

    }
}
