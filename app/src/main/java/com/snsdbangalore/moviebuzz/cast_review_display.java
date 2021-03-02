package com.snsdbangalore.moviebuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class cast_review_display extends AppCompatActivity {

    TextView activityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_review_display);
        activityTitle = findViewById(R.id.displayTitle);
        Intent intent = getIntent();
        String titleDp = intent.getStringExtra("check");
        activityTitle = findViewById(R.id.displayTitle);
        activityTitle.setText(titleDp);
    }
}