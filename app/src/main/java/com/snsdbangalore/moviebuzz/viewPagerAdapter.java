package com.snsdbangalore.moviebuzz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class viewPagerAdapter extends PagerAdapter {
    private ArrayList<String> pictures;
    private ArrayList<String> IDs;
    private ArrayList<String> name;
    private ArrayList<String> release;
    private Context context;
    final String link = "https://image.tmdb.org/t/p/w500";

    public viewPagerAdapter(Context context, ArrayList<String> IDs,ArrayList<String> name,ArrayList<String> pictures,ArrayList<String> release) {
        this.context = context;
        this.pictures = pictures;
        this.IDs = IDs;
        this.name = name;
        this.release = release;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viepager_filler,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        TextView titleText = (TextView)view.findViewById(R.id.movie_title);
        TextView releasetext = (TextView)view.findViewById(R.id.release_date);
        titleText.setText(name.get(position));
        releasetext.setText(release.get(position));
        Glide.with(context).load(link+ pictures.get(position)).into(imageView);
        container.addView(view);
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImageView i = (ImageView)v;
                Intent intent = new Intent(context,MovieDescription.class);
                intent.putExtra("id",IDs.get(position));
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
