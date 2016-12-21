package com.example.yusuf.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yusuf on 13.11.2016.
 */
public class Vine {

    private int vineNum = -1; //vine Number
    private int headPos = -1; //Start of vine
    private int tailPos = -1; // End of vine
    Context context;     // Context allows us to access the android system resources and will allow us to get the picture of the vine
    Bitmap vineChar;   // vine picture

    //Constructor
    public Vine(int num, int head, int tail, Context current){
        context = current;
        vineNum = num;
        headPos = head;
        tailPos = tail;
        setvine(); // setvine method is called in constructor
    }

    public int getHead(){
        return headPos;
    }

    public int getTail(){
        return tailPos;
    }

    //setvine method gets the picture from the Resources file of android and sets the right picture of the vine according to where the vine points
    public void setvine(){
        vineChar = BitmapFactory.decodeResource(context.getResources(),R.drawable.vine);
    }

    public Bitmap getvine(){
        return vineChar;
    }
}
