package com.snsdbangalore.moviebuzz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class recyclerViewForReview extends RecyclerView.Adapter<recyclerViewForReview.reviewViewHolder> {
    Context context;
    private ArrayList<String> authorIMG;
    private ArrayList<String> authorName;
    private ArrayList<String> authorCreate;
    private ArrayList<String> authorContent;

    public recyclerViewForReview(Context context, ArrayList<String> authorIMG, ArrayList<String> authorName, ArrayList<String> authorCreate, ArrayList<String> authorContent) {
        this.context = context;
        this.authorIMG = authorIMG;
        this.authorName = authorName;
        this.authorCreate = authorCreate;
        this.authorContent = authorContent;
    }

    @NonNull
    @Override
    public reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_display, parent, false);
        reviewViewHolder rVH = new reviewViewHolder(v);
        return rVH;
    }

    @Override
    public void onBindViewHolder(@NonNull reviewViewHolder holder, int position) {
        Glide.with(context).load(authorIMG.get(position)).into(holder.imgAuthor);
        holder.titleauthor.setText(authorName.get(position));
        holder.reviewCreatedate.setText(authorCreate.get(position));
        holder.reviewContent.setText(authorContent.get(position));
    }

    @Override
    public int getItemCount() {
        return authorName.size();
    }

    public static class reviewViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgAuthor;
        public TextView titleauthor;
        public TextView reviewCreatedate;
        public TextView reviewContent;

        public reviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAuthor = itemView.findViewById(R.id.photo_reviewer);
            titleauthor = itemView.findViewById(R.id.author_name);
            reviewCreatedate = itemView.findViewById(R.id.created_date);
            reviewContent = itemView.findViewById(R.id.content);
        }
    }
}
