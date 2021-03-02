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

public class rvProdAdapter extends RecyclerView.Adapter<rvProdAdapter.prodViewHolder> {
    ArrayList<String> ProductionIMG;
    ArrayList<String> productionTitle;
    Context context;

    public rvProdAdapter(Context context, ArrayList<String> ProductionIMG, ArrayList<String> ProductionTitle) {
        this.context = context;
        this.ProductionIMG = ProductionIMG;
        this.productionTitle = ProductionTitle;
    }

    @NonNull
    @Override
    public prodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.production_similar, parent, false);
        prodViewHolder PVH = new prodViewHolder(v);
        return PVH;
    }

    @Override
    public void onBindViewHolder(@NonNull prodViewHolder holder, int position) {
        holder.titleProd.setText(productionTitle.get(position));
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + ProductionIMG.get(position)).into(holder.imgProd);
    }

    @Override
    public int getItemCount() {
        return ProductionIMG.size();
    }

    public static class prodViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProd;
        public TextView titleProd;

        public prodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProd = (ImageView) itemView.findViewById(R.id.square_image);
            titleProd = (TextView) itemView.findViewById(R.id.titleProdSimilar);
        }
    }
}
