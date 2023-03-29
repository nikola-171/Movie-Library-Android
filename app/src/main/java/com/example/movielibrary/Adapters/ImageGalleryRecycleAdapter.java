package com.example.movielibrary.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielibrary.Models.Images.ImageItemModel;
import com.example.movielibrary.R;
import com.example.movielibrary.Shared.Helper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;
import java.util.Objects;

public class ImageGalleryRecycleAdapter extends RecyclerView.Adapter<ImageGalleryViewHolder> {
    private final Context context;
    private final List<ImageItemModel> list;

    public ImageGalleryRecycleAdapter(Context context, List<ImageItemModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ImageGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageGalleryViewHolder(LayoutInflater.from(context).inflate(R.layout.image_gallery_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGalleryViewHolder holder, int position) {
        if(list.get(position).getImage() != null && !Objects.equals(list.get(position).getImage(), "")){
            try {
                RequestCreator image =  Picasso.get().load(list.get(position).getImage());

                Picasso.get().load(list.get(position).getImage()).resize(500, 600).placeholder(R.drawable.ic_baseline_image_500x600).into(holder.imageView_imageGalleryItem, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageView_imageGalleryItem.setOnClickListener(view -> {
                            final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                            dialog.setContentView(R.layout.image_dialog);

                            ImageView imageView = dialog.findViewById(R.id.imageView_imageDialog);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                            image.resize(Helper.getScreenWidth()-35, Helper.getScreenHeight()-35).into(imageView);

                            TextView close = dialog.findViewById(R.id.textView_closeImageFullscreen);
                            close.setOnClickListener(view1 -> dialog.dismiss());

                            dialog.show();
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}

class ImageGalleryViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_imageGalleryItem;

    public ImageGalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_imageGalleryItem = itemView.findViewById(R.id.imageView_imageGalleryItem);

    }
}
