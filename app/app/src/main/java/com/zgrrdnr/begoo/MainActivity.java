package com.zgrrdnr.begoo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

// BeGoo --> Bellek Gelistirme ve Ogrenme Oyunu - Feb 2021 - Ozgur Erdener

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int    GRID_LAYOUT_X_RATIO = 60;
    private static final int    GRID_LAYOUT_Y_RATIO = 60;

    private Button              toPgButton;
    private Image[]             dbRowItems;
    private int                 selectedCategoryIndex = 0;
    private GridLayout          gridLayout;
    private ImageButton[]       categoryButton;
    private PlayGroundAdapter   playGroundAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(android.R.attr.windowFullscreen, 0);

        setContentView(R.layout.activity_main);

        toPgButton = (Button)findViewById(R.id.toPlayGround);

        toPgButton.setOnClickListener(this);

        playGroundAdapter = new PlayGroundAdapter(this);

        dbRowItems = playGroundAdapter.getDbRowItems();

        /* First row is for background picture */
        ((ConstraintLayout)findViewById(R.id.macl)).setBackground(new BitmapDrawable(getResources(), dbRowItems[0].getBmp()));

        arrangeLayout();
    }

    private void arrangeLayout()
    {
        categoryButton = new ImageButton[dbRowItems.length - 1];

        GridAndImgProperties gridAndImgProperties = Controller.getGridAndImgProperties(categoryButton.length, GRID_LAYOUT_X_RATIO, GRID_LAYOUT_Y_RATIO);

        gridLayout = (GridLayout) findViewById(R.id.gl);

        gridLayout.setColumnCount(gridAndImgProperties.columnCount);
        gridLayout.setRowCount(gridAndImgProperties.rowCount);

        for(int i = 0; i < categoryButton.length; i++)
        {
            categoryButton[i] = new ImageButton(this);

            categoryButton[i].setOnClickListener(this);
            categoryButton[i].setImageBitmap(BmpProcessor.scaleBmp(gridAndImgProperties.imgXScaleFactor, gridAndImgProperties.imgYScaleFactor, dbRowItems[i + 1].getBmp()));

            gridLayout.addView(categoryButton[i]);
        }
    }

    @Override
    public void onClick(View btn)
    {
        if(btn == toPgButton)
        {
            playGroundAdapter.launchPlayGround(selectedCategoryIndex, "default user");
            return;
        }

        for(int i = 0; i < categoryButton.length; i++)
        {
            if (categoryButton[i] == btn)
            {
                selectedCategoryIndex = i;

                return;
            }
        }
    }
}