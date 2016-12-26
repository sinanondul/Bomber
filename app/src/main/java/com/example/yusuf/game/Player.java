package com.example.yusuf.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yusuf on 10.11.2016.
 */
public class Player {
    String playerName;
    int blockVal, prevBlock; // Is the block that the player is currently on
    boolean gender;
    Bitmap character;
    Context context; //Check Snake Class for explanation of Context
    int itemType;      // The Item that the player currently has
    int playerNum;

    public  Player( String name, boolean isMale, Context current, int num){
        playerName = name;
        gender = isMale;
        blockVal = 1;// Block Val is set to one because player will start from block 1 when created
        prevBlock = 1;
        playerNum = num;
        this.context = current;
        if (isMale) //Sets the right image of the player depending on gender
            character = BitmapFactory.decodeResource(context.getResources(),R.drawable.male);
        else
            character = BitmapFactory.decodeResource(context.getResources(),R.drawable.female);
        itemType = 0;
    }

    public String getName(){
        return playerName;
    }

    public int getBlock(){
        return blockVal;
    }

    public boolean getGender(){
        return gender;
    }

    public int getItemType(){
        return itemType;
    }

    public  int getPrevBlock(){ return prevBlock;}

    public Bitmap getCharacter(){
        return character;
    }

    //This method updates the player's position and
    public void updatePos( int val){
        prevBlock = blockVal;
        blockVal += val;
        if(blockVal > 100)
            blockVal = 100;
        if(blockVal < 0)
            blockVal = 0;
    }

    public void setPos( int val){
        prevBlock = blockVal;
        blockVal = val;
        if(blockVal > 100)
            blockVal = 100;
        if(blockVal < 0)
            blockVal = 0;
    }
    // Sets the item as an integer, where all the integers correspond to an item.
    public void setItemType( int val){
        if ( val == 0) // Checks if the value being entered is -1, if it is then it means player holds no items
            itemType = 0;
        else if( itemType == 0){ // Here it checks if the itme type is -1, if it is then player can get new item. If not -1, then it means that the player already has some item and cont pick up a new item.
            itemType = val;
        }
    }

    public int getPlayerNum(){
        return playerNum;
    }

}
