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
    String url;
    TextView title,language,vote,popularity,release,releaseStatus,overView;
    ImageView imgPoster;
    Button cast,review;
    RecyclerView rvProduction,rvSimilar;

    RecyclerView.LayoutManager gridLayoutProduction = new GridLayoutManager(this,3);
    RecyclerView.LayoutManager gridLayoutSimilar = new GridLayoutManager(this,3);



    ArrayList<String> ProductionIMG = new ArrayList<String>();
    ArrayList<String> ProductionTitle = new ArrayList<String>();
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

        url = "https://api.themoviedb.org/3/movie/"+intent.getStringExtra("id")+"?api_key=7de7c3b1f16e07ca7b623cf99e25e505";


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
                    for(int i =0;i<jsonProd.length();i++){
                        JSONObject prodObj = jsonProd.getJSONObject(i);
                        ProductionIMG.add(prodObj.getString("logo_path"));
                        ProductionTitle.add(prodObj.getString("name"));
                    }
                    rvProduction.setLayoutManager(gridLayoutProduction);
                    RecyclerView.Adapter prodAdapt = new rvProdAdapter(getApplicationContext(),ProductionIMG,ProductionTitle);
                    rvProduction.setAdapter(prodAdapt);
                }catch (JSONException e){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong", LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}