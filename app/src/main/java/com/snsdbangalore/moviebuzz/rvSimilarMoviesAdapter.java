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

public class rvSimilarMoviesAdapter extends RecyclerView.Adapter<rvSimilarMoviesAdapter.similarMoviesHolder> {
    Context context;
    ArrayList<String> SimilarMoviesIMG;
    ArrayList<String> SimilarMoviesTitle;
    ArrayList<String> SimilarMoviesIds;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public rvSimilarMoviesAdapter(Context context, ArrayList<String> SimilarMoviesIMG, ArrayList<String> SimilarMoviesTitle, ArrayList<String> SimilarMoviesIds) {
        this.context = context;
        this.SimilarMoviesIMG = SimilarMoviesIMG;
        this.SimilarMoviesTitle = SimilarMoviesTitle;
        this.SimilarMoviesIds = SimilarMoviesIds;
    }

    @NonNull
    @Override
    public similarMoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.production_similar, parent, false);
        similarMoviesHolder sMH = new similarMoviesHolder(v, mListener);
        return sMH;
    }

    @Override
    public void onBindViewHolder(@NonNull similarMoviesHolder holder, int position) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + SimilarMoviesIMG.get(position)).into(holder.similarMoviesImage);
        holder.similarMoviestext.setText(SimilarMoviesTitle.get(position));
    }

    @Override
    public int getItemCount() {
        return SimilarMoviesIds.size();
    }

    public static class similarMoviesHolder extends RecyclerView.ViewHolder {
        ImageView similarMoviesImage;
        TextView similarMoviestext;

        public similarMoviesHolder(@NonNull View itemView, final OnItemClickListener mListner) {
            super(itemView);
            similarMoviesImage = (ImageView) itemView.findViewById(R.id.square_image);
            similarMoviestext = (TextView) itemView.findViewById(R.id.titleProdSimilar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
