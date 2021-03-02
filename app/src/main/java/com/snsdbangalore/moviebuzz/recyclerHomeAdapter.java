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

public class recyclerHomeAdapter extends RecyclerView.Adapter<recyclerHomeAdapter.homeViewHolder> {
    Context context;
    ArrayList<String> idsPopular = new ArrayList<String>();
    ArrayList<String> namePopular = new ArrayList<String>();
    ArrayList<String> picturesPopular = new ArrayList<String>();
    ArrayList<String> releasePopular = new ArrayList<String>();
    ArrayList<String> votesPopular = new ArrayList<String>();
    ArrayList<String> languagePopular = new ArrayList<String>();
    final String link = "https://image.tmdb.org/t/p/w500";

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public recyclerHomeAdapter(Context context, ArrayList<String> idsPopular, ArrayList<String> namePopular, ArrayList<String> picturesPopular, ArrayList<String> releasePopular, ArrayList<String> votesPopular, ArrayList<String> languagePopular) {
        this.idsPopular = idsPopular;
        this.context = context;
        this.namePopular = namePopular;
        this.picturesPopular = picturesPopular;
        this.releasePopular = releasePopular;
        this.votesPopular = votesPopular;
        this.languagePopular = languagePopular;
    }

    @NonNull
    @Override
    public homeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rv_homepage, parent, false);
        homeViewHolder hVH = new homeViewHolder(v, mListener);
        return hVH;
    }

    @Override
    public void onBindViewHolder(@NonNull homeViewHolder holder, int position) {
        String picId = picturesPopular.get(position);
        Glide.with(context).load(link + picId).into(holder.imgPoster);
        holder.title.setText(namePopular.get(position));
        holder.votes.setText(votesPopular.get(position));
        holder.language.setText(languagePopular.get(position));
        holder.release.setText(releasePopular.get(position));
    }

    @Override
    public int getItemCount() {
        return idsPopular.size();
    }

    public static class homeViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgPoster;
        public TextView title, votes, language, release;

        public homeViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgPoster = (ImageView) itemView.findViewById(R.id.img_poster_in_rv);
            title = (TextView) itemView.findViewById(R.id.rv_tite_home);
            votes = (TextView) itemView.findViewById(R.id.rv_vote_home);
            language = (TextView) itemView.findViewById(R.id.rv_language_home);
            release = (TextView) itemView.findViewById(R.id.rv_release_home);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
