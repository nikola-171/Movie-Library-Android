package com.example.movielibrary.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Models.FaqModels.FaqItemModel;
import com.example.movielibrary.R;

import java.util.List;

public class FaqRecycleAdapter extends RecyclerView.Adapter<FaqViewHolder> {

    Context context;
    List<FaqItemModel> list;

    public FaqRecycleAdapter(Context context, List<FaqItemModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqViewHolder(LayoutInflater.from(context).inflate(R.layout.faq_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        holder.textView_answer.setText(list.get(position).getAnswer());
        holder.textView_question.setText(list.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

class FaqViewHolder extends RecyclerView.ViewHolder {

    TextView textView_question, textView_answer;

    public FaqViewHolder(@NonNull View itemView) {
        super(itemView);

        textView_question = itemView.findViewById(R.id.textView_question);
        textView_answer = itemView.findViewById(R.id.textView_answer);

    }
}
