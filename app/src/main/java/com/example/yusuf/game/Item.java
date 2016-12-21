package com.example.yusuf.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yusuf on 16.12.2016.
 */
public class Item {

    int blockPos, itemType;
    boolean isvisible;
    Bitmap itemImage;


    public Item(int pos, int type, boolean visible, Context context){
        blockPos = pos;
        isvisible = visible;
        itemType = type;

        if( itemType == 1){
            itemImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.peal);
        }
        else if( itemType == 2){
            itemImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.beaker);
        }
        else
            itemImage = BitmapFactory.decodeResource(context.getResources(),R.drawable.oil);
    }

    public int getBlockPos() {
        return blockPos;
    }

    public int getItemType() {
        return itemType;
    }

    public boolean getIsVisible(){
        return isvisible;
    }

    public Bitmap getItem(){
        return itemImage;
    }

    public void setIsvisible( boolean visible){
        isvisible = visible;
    }
}
