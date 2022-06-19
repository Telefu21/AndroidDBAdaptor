package com.zgrrdnr.begoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener
{
    private int     score, totalScore;
    private int     selectedCategoryIndex;
    private String  userName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Button toPgButton = (Button) findViewById(R.id.toPGFromScore);

        toPgButton.setOnClickListener(this);

        Intent intent = getIntent();

        score = intent.getIntExtra(InterActivityData.NAME_SCORE,0);
        totalScore = intent.getIntExtra(InterActivityData.NAME_TOTAL_SCORE,0);
        selectedCategoryIndex = intent.getIntExtra(InterActivityData.NAME_SELECTED_CATEGORY,0);
        userName = intent.getStringExtra(InterActivityData.NAME_USER_NAME);
    }

    @Override
    public void onClick(View v)
    {
        PlayGroundAdapter playGroundAdapter = new PlayGroundAdapter(this);

        playGroundAdapter.launchPlayGround(selectedCategoryIndex, userName);
    }
}