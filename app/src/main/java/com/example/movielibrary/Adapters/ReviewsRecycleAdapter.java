package com.example.movielibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Models.ReviewsModel.ReviewItemModel;
import com.example.movielibrary.R;

import java.util.List;

public class ReviewsRecycleAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {
    private final List<ReviewItemModel> list;
    private final Context context;

    public ReviewsRecycleAdapter(Context context, List<ReviewItemModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewsViewHolder(LayoutInflater.from(context).inflate(R.layout.reviews_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        ReviewItemModel item = this.list.get(position);

        holder.textView_username.setText(item.getUsername());
        holder.textView_userRating.setText(item.getRate());
        holder.textView_reviewDate.setText(item.getDate());
        holder.textView_userReviewTitle.setText(item.getTitle());
        holder.textView_userReviewContent.setText(item.getContent());
        holder.textView_userReviewUseful.setText(item.getHelpful());

        if(item.getWarningSpoilers()){
            holder.textView_spoilerAlert.setVisibility(View.VISIBLE);
        }else{
            holder.textView_spoilerAlert.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

class ReviewsViewHolder extends RecyclerView.ViewHolder {

    TextView textView_username, textView_userRating, textView_reviewDate,
             textView_userReviewTitle, textView_userReviewContent,
             textView_userReviewUseful, textView_spoilerAlert;

    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_username = itemView.findViewById(R.id.textView_username);
        textView_userRating = itemView.findViewById(R.id.textView_userRating);
        textView_reviewDate = itemView.findViewById(R.id.textView_reviewDate);
        textView_userReviewContent = itemView.findViewById(R.id.textView_userReviewContent);
        textView_userReviewTitle = itemView.findViewById(R.id.textView_userReviewTitle);
        textView_userReviewUseful = itemView.findViewById(R.id.textView_userReviewUseful);
        textView_spoilerAlert = itemView.findViewById(R.id.textView_spoilerAlert);

    }
}
