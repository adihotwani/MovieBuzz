package com.snsdbangalore.moviebuzz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class MovieDescription extends AppCompatActivity {
    String url,id;
    TextView title,language,vote,popularity,release,releaseStatus,overView;
    ImageView imgPoster;
    Button cast,review;
    RecyclerView rvProduction,rvSimilar;

    RecyclerView.LayoutManager gridLayoutProduction = new GridLayoutManager(this,3);
    RecyclerView.LayoutManager gridLayoutSimilar = new GridLayoutManager(this,3);

    RequestQueue requestQueue;

    ArrayList<String> ProductionIMG = new ArrayList<String>();
    ArrayList<String> ProductionTitle = new ArrayList<String>();
    ArrayList<String> SimilarMoviesIMG = new ArrayList<String>();
    ArrayList<String> SimilarMoviesTitle = new ArrayList<String>();
    ArrayList<String> SimilarMoviesIds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        Intent intent = getIntent();

        title = findViewById(R.id.movie_title);
        language = findViewById(R.id.language_disp);
        vote = findViewById(R.id.vote_disp);
        popularity = findViewById(R.id.popularity_disp);
        release = findViewById(R.id.release_date);
        releaseStatus = findViewById(R.id.release_status);
        overView = findViewById(R.id.overview_disp);

        imgPoster = findViewById(R.id.image_poster);

        cast = findViewById(R.id.button_cast);
        review = findViewById(R.id.button_review);

        rvProduction = findViewById(R.id.recycler_inside_production);
        rvSimilar = findViewById(R.id.recycler_similar_movies);

        id = intent.getStringExtra("id");
        url = "https://api.themoviedb.org/3/movie/"+id+"?api_key=7de7c3b1f16e07ca7b623cf99e25e505";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    title.setText(jsonObject.getString("original_title"));
                    language.setText(jsonObject.getString("original_language"));
                    vote.setText(jsonObject.getString("vote_average"));
                    popularity.setText(jsonObject.getString("popularity"));
                    release.setText(jsonObject.getString("release_date"));
                    releaseStatus.setText(jsonObject.getString("status"));
                    overView.setText(jsonObject.getString("overview"));

                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path")).into(imgPoster);

                    JSONArray jsonProd = jsonObject.getJSONArray("production_companies");

                    prodctionDisplay(jsonProd);


                    similarDisplay();

                }catch (JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong", LENGTH_LONG).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    void prodctionDisplay(JSONArray jsonArray){
        for(int i =0;i<jsonArray.length();i++){
            JSONObject prodObj = null;
            try {
                prodObj = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ProductionIMG.add(prodObj.getString("logo_path"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ProductionTitle.add(prodObj.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        rvProduction.setLayoutManager(gridLayoutProduction);
        RecyclerView.Adapter prodAdapt = new rvProdAdapter(getApplicationContext(),ProductionIMG,ProductionTitle);
        rvProduction.setAdapter(prodAdapt);
    }
    void similarDisplay(){
        String similarLink = "https://api.themoviedb.org/3/movie/"+id+"/similar?api_key=7de7c3b1f16e07ca7b623cf99e25e505&page=1";
        StringRequest similarMoviesRequest = new StringRequest(Request.Method.GET, similarLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject similarObject = new JSONObject(String.valueOf(response));
                    JSONArray similarArray = similarObject.getJSONArray("results");
                    for(int i=0;i<similarArray.length();i++){
                        JSONObject similarMoviesObject = similarArray.getJSONObject(i);
                        SimilarMoviesIMG.add(similarMoviesObject.getString("poster_path"));
                        SimilarMoviesTitle.add(similarMoviesObject.getString("original_title"));
                        SimilarMoviesIds.add(similarMoviesObject.getString("id"));
                    }
                    rvSimilar.setLayoutManager(gridLayoutSimilar);
                    rvSimilarMoviesAdapter similarMoviesAdapter = new rvSimilarMoviesAdapter(getApplicationContext(),SimilarMoviesIMG,SimilarMoviesTitle,SimilarMoviesIds);
                    rvSimilar.setAdapter(similarMoviesAdapter);
                    similarMoviesAdapter.setOnItemClickListener(new rvSimilarMoviesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent = new Intent(MovieDescription.this,MovieDescription.class);
                            intent.putExtra("id",SimilarMoviesIds.get(position));
                            startActivity(intent);
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
        requestQueue.add(similarMoviesRequest);
    }
}