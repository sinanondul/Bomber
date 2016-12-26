package com.example.yusuf.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        TextView text = (TextView)findViewById(R.id.txtHelp);
        text.setMovementMethod( new ScrollingMovementMethod());

    }
}
