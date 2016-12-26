package com.example.yusuf.game;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class DiceDisplay extends AppCompatActivity {

    ImageView dice;
    TextView value;
    int val;
    Random rand = new Random();
    final int images [] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6 };
    AnimationDrawable animation;
    String roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_display);



        if (roll == null)
            Log.d("valuePassed", "NONE");
        else
            Log.d("valuePassed", "4");

        startAnimation();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                finish();
            }
        }, 1000);

    }

    class Starter implements Runnable {
        public void run() {
            animation.start();
        }
    }

    private void startAnimation(){
        animation = new AnimationDrawable();
        animation.addFrame(getResources().getDrawable(R.drawable.dice6), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice3), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice4), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice5), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice2), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice1), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice5), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice1), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice3), 100);
        animation.addFrame(getResources().getDrawable(R.drawable.dice6), 100);
        animation.setOneShot(true);

        ImageView imageView = (ImageView) findViewById(R.id.imgRoll);

        imageView.setImageDrawable(animation);
        imageView.post(new Starter());
    }
}
