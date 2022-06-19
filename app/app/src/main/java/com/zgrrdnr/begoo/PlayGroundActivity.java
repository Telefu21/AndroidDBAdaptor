package com.zgrrdnr.begoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

public class PlayGroundActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(android.R.attr.windowFullscreen, 0);

        setContentView(R.layout.activity_play_ground);

        Intent intent = getIntent();
        InterActivityData params = intent.getParcelableExtra("InterActData");

        Controller c = new Controller(this, (GridLayout) findViewById(R.id.gridLayout), params);
    }
}