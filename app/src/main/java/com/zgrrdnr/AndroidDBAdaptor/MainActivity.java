package com.zgrrdnr.AndroidDBAdaptor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener
{
    private Button          toPgButton;
    private TextView        textView;
    private ImageView       imageView;
    private HttpsHandler    handler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toPgButton = (Button) findViewById(R.id.button);
        imageView = (ImageView)findViewById(R.id.iv);

        toPgButton.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.text);

        handler = new HttpsHandler(this);
    }

    @Override
    public void onClick(View v)
    {
        handler.connect();
    }
}