package com.example.yusuf.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainStart extends AppCompatActivity {

    Button play, help, exit;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        play = (Button)findViewById(R.id.btnPlay);
        exit = (Button)findViewById(R.id.btnExit);
        help = (Button)findViewById(R.id.btnHelp);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(MainStart.this, GameMode.class);
                startActivity(in);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = new Intent(MainStart.this, Help.class);
                startActivity(in);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(MainStart.this, Credits.class);
                startActivity(in);
            }
        });
    }

}
