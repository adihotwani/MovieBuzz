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

public class castDisplayAdapter extends RecyclerView.Adapter<castDisplayAdapter.castViewHolder> {
    ArrayList<String> castProfile;
    Context context;
    ArrayList<String> castName;
    ArrayList<String> castCharacter;

    public castDisplayAdapter(Context context, ArrayList<String> castProfile, ArrayList<String> castName, ArrayList<String> castCharacter) {
        this.context = context;
        this.castProfile = castProfile;
        this.castName = castName;
        this.castCharacter = castCharacter;
    }

    @NonNull
    @Override
    public castViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_layout_adapt, parent, false);
        castViewHolder CVH = new castViewHolder(v);
        return CVH;
    }

    @Override
    public void onBindViewHolder(@NonNull castViewHolder holder, int position) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + castProfile.get(position)).into(holder.castImage);
        holder.textViewName.setText(castName.get(position));
        holder.textViewChar.setText(castCharacter.get(position));
    }

    @Override
    public int getItemCount() {
        return castName.size();
    }

    public static class castViewHolder extends RecyclerView.ViewHolder {
        ImageView castImage;
        TextView textViewName;
        TextView textViewChar;

        public castViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.square_image_cast);
            textViewName = itemView.findViewById(R.id.titleCast);
            textViewChar = itemView.findViewById(R.id.characterCast);
        }
    }
}
