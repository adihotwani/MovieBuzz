package com.snsdbangalore.moviebuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Button buttonPrev,buttonNext;

    final ArrayList<String> ids = new ArrayList<String>();
    final ArrayList<String> name = new ArrayList<String>();
    final ArrayList<String> pictures = new ArrayList<String>();
    final ArrayList<String> release = new ArrayList<String>();

    final ArrayList<String> idsPopular = new ArrayList<String>();
    final ArrayList<String> namePopular = new ArrayList<String>();
    final ArrayList<String> picturesPopular = new ArrayList<String>();
    final ArrayList<String> releasePopular = new ArrayList<String>();
    final ArrayList<String> votesPopular = new ArrayList<String>();
    final ArrayList<String> languagePopular = new ArrayList<String>();


    int PageNumber = 1;

    private Handler handler;
    private int delay = 5000; //milliseconds
    viewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    private int page = 0;
    Runnable runnable = new Runnable() {
        public void run() {
            if (viewPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            viewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        TabLayout tabDots = findViewById(R.id.tabDots);
        tabDots.setupWithViewPager(viewPager,true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);



        StringRequest stringRequestNowPlaying = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/now_playing?api_key=7de7c3b1f16e07ca7b623cf99e25e505&page="+String.valueOf(PageNumber), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);
                        ids.add(jsonObject.getString("id"));
                        name.add(jsonObject.getString("original_title"));
                        pictures.add(jsonObject.getString("backdrop_path"));
                        release.add(jsonObject.getString("release_date"));
                    }
                    viewPagerAdapter = new viewPagerAdapter(MainActivity.this,ids,name,pictures,release);
                    viewPager.setAdapter(viewPagerAdapter);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            page = position;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequestNowPlaying);


        StringRequest stringRequestPopular = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/popular?api_key=7de7c3b1f16e07ca7b623cf99e25e505&page="+String.valueOf(PageNumber), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                   JSONObject popularJsonObject = new JSONObject(response);
                   JSONArray popularJsonArrat = popularJsonObject.getJSONArray("results");
                   for(int j=0;j<popularJsonArrat.length();j++){
                       JSONObject popularMoviesObject = popularJsonArrat.getJSONObject(j);
                       idsPopular.add(popularMoviesObject.getString("id"));
                       namePopular.add(popularMoviesObject.getString("original_title"));
                       picturesPopular.add(popularMoviesObject.getString("poster_path"));
                       releasePopular.add(popularMoviesObject.getString("release_date"));
                       votesPopular.add(popularMoviesObject.getString("vote_average"));
                       languagePopular.add(popularMoviesObject.getString("original_language"));
                   }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerHomeAdapter homeAdapter = new recyclerHomeAdapter(getApplicationContext(),idsPopular,namePopular,picturesPopular,releasePopular,votesPopular,languagePopular);

                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(homeAdapter);
                    homeAdapter.setOnItemClickListener(new recyclerHomeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(MainActivity.this,MovieDescription.class);
                            intent.putExtra("id",idsPopular.get(position));
                            startActivity(intent);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequestPopular);
    }
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

}