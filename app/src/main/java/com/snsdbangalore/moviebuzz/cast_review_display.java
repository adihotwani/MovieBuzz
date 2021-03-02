package com.snsdbangalore.moviebuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class cast_review_display extends AppCompatActivity {

    TextView activityTitle;
    RecyclerView castReviewRV;

    RecyclerView.LayoutManager castlayout;
    RecyclerView.LayoutManager reviewLayout;

    RequestQueue requestQueue;

    ArrayList<String> castProfile = new ArrayList<>();
    ArrayList<String> castName = new ArrayList<>();
    ArrayList<String> castCharacter = new ArrayList<>();

    ArrayList<String> authorProfile = new ArrayList<>();
    ArrayList<String> authorName = new ArrayList<>();
    ArrayList<String> authorCreate = new ArrayList<>();
    ArrayList<String> authorContent = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_review_display);

        final String Casts = "Casts";
        final String Reviews = "Reviews";

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        activityTitle = findViewById(R.id.displayTitle);
        castReviewRV = findViewById(R.id.recycler_cast_review);

        Intent intent = getIntent();
        String titleDp = intent.getStringExtra("check");
        String id = intent.getStringExtra("id");

        activityTitle = findViewById(R.id.displayTitle);
        activityTitle.setText(titleDp);


        castlayout = new GridLayoutManager(getApplicationContext(), 3);
        reviewLayout = new LinearLayoutManager(getApplicationContext());

        if (titleDp.equals(Casts)) {
            displayCasts(id);
        } else if (titleDp.equals(Reviews)) {
            displayReviews(id);
        }
    }

    private void displayReviews(String id) {
        StringRequest stringRequestReviews = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=7de7c3b1f16e07ca7b623cf99e25e505&language=en-US&page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("author_details");
                        authorProfile.add(jsonObject2.getString("avtar_path"));
                        authorName.add(jsonObject1.getString("author"));
                        authorContent.add(jsonObject1.getString("content"));
                        authorCreate.add(jsonObject1.getString("created_at"));
                    }
                    castReviewRV.setLayoutManager(reviewLayout);
                    RecyclerView.Adapter recyclerViewforReview = new recyclerViewForReview(getApplicationContext(), authorProfile, authorName, authorCreate, authorContent);
                    castReviewRV.setAdapter(recyclerViewforReview);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequestReviews);
    }

    private void displayCasts(String id) {
        StringRequest stringRequestCast = new StringRequest(Request.Method.GET, "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=7de7c3b1f16e07ca7b623cf99e25e505", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("cast");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        castProfile.add(jsonObject1.getString("profile_path"));
                        castName.add(jsonObject1.getString("name"));
                        castCharacter.add(jsonObject1.getString("character"));
                    }
                    castReviewRV.setLayoutManager(castlayout);
                    RecyclerView.Adapter castDisplayAdapter = new castDisplayAdapter(getApplicationContext(), castProfile, castName, castCharacter);
                    castReviewRV.setAdapter(castDisplayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequestCast);
    }
}