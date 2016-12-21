package com.example.yusuf.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by yusuf on 12.10.2016.
 */
public class Board extends View {
    public final int NUM_BLOCKS = 100;
    public final int SNAKE_LIST[] = { 99,49,88,51, 80,16, 36,10, 74, 45, 50,28, 37, 2};
    public final int VINE_LIST[] = {22,46, 58,76, 30,33, 70,87};
    public final int ITEM_LIST[] = { 40,1,72,1,82,1,42,2,15,2, 68,2, 28,3,55,3,3,3};
    public final int SNAKE_NUM = 7;
    public final int VINE_NUM = 4;
    public final int ITEM_NUM = 9;
    ArrayList<Block> blocks= new ArrayList<Block>();
    private Paint paint, paintFont;
    private int width, totWidth;
    private int blockVal = NUM_BLOCKS;
    private Player player1, player2;
    ArrayList<Snake> snakes = new ArrayList<Snake>();
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Vine> vines = new ArrayList<Vine>();
    boolean animate = false;


    public Board(Context context, Player play1, Player play2, int type){

        super(context);

        paint = new Paint();
        paintFont = new Paint();
        player1 = play1;
        player2 = play2;

        //Initialising Snakes
        for (int i = 0; i < SNAKE_LIST.length; i+=2){
            snakes.add( new Snake(i, SNAKE_LIST[i], SNAKE_LIST[i+1],context));
        }

        //Initialising Items
        for (int i = 0; i < ITEM_LIST.length; i+=2){
            items.add( new Item(ITEM_LIST[i], ITEM_LIST[i+1], true,context));
        }

        //Initialising Vines
        for (int i = 0; i < VINE_LIST.length; i+=2){
            vines.add( new Vine(i, VINE_LIST[i], VINE_LIST[i+1],context));
        }


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        width = display.getWidth();
        totWidth = width;
        width = width/12;
        int height = getHeight();
        for(int i = 0; i < 10; i++) {
            if( i %2 == 0){
                for(int j = 0; j < 10; j++) {

                    int left = j * (width + 15);
                    int top = i *(width + 10);
                    int right = left + width;
                    int bottom = top + width;

                    blocks.add( new Block(new Rect(left,top,right,bottom), blockVal));
                    blockVal--;
                }
            }
            else {
                for(int j = 9; j >= 0; j--) {

                    int left = j * (width + 15);
                    int top = i *(width + 10);
                    int right = left + width;
                    int bottom = top + width;

                    blocks.add( new Block(new Rect(left,top,right,bottom), blockVal));
                    blockVal--;
                }

            }
        }


        paint.setColor(Color.parseColor("#009688"));
        paintFont.setColor(Color.parseColor("#ffffff"));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int head,tail;
        double objectLength;
        Matrix transform;
        float acc = 0.2f; //acceleration
        float dy = 0; //vertical speed
        int initialY = 100; //Initial vertical position.

        super.onDraw(canvas);

        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Bitmap player1Char = player1.getCharacter();
        Bitmap player2Char = player2.getCharacter();
        //background = getResizedBitmap(background,width, width);
        canvas.drawBitmap(Bitmap.createScaledBitmap(background,totWidth, totWidth,false),0,0,null);

        //Drawing Blocks
        for( int i = 0; i < 100; i++){

            canvas.drawRect(blocks.get(i).getCoordinates(), paint);
        }

        // Drawing Snakes
        for  ( int i = 0 ; i < SNAKE_NUM; i++) {
            head = snakes.get(i).getHead();
            tail = snakes.get(i).getTail();

            objectLength = getObjectLength(head,tail);
            transform = setObjects(head,tail,objectLength, 1);

            canvas.drawBitmap(Bitmap.createScaledBitmap(snakes.get(i).getSnake(), (width), (int) objectLength, false), transform, null);

        }

        // Drawing Vine
        for  ( int i = 0 ; i < VINE_NUM; i++) {
            head = vines.get(i).getTail();
            tail = vines.get(i).getHead();

            objectLength = getObjectLength(head,tail);
            transform = setObjects(head,tail,objectLength, 2);

            canvas.drawBitmap(Bitmap.createScaledBitmap(vines.get(i).vineChar , (width), (int) objectLength, false), transform, null);

        }

        // Drawing Items
        for (int i = 0; i < ITEM_NUM; i++){
            head = items.get(i).getBlockPos();

            if (items.get(i).getIsVisible()){
                canvas.drawBitmap(Bitmap.createScaledBitmap(items.get(i).getItem(), (width), width, false),  blocks.get(100- head).getCoordinates().centerX() - width/2, blocks.get(100 -head).getCoordinates().centerY()- width/2, null);
            }
        }

        //Draw Player1
        canvas.drawBitmap(Bitmap.createScaledBitmap(player1Char,width,width, false),(blocks.get( 100 -player1.getBlock()).getCoordinates().centerX()-width/2),(blocks.get( 100 -player1.getBlock()).getCoordinates().centerY() - width/2), null);
        //Draw Player2
        canvas.drawBitmap(Bitmap.createScaledBitmap(player2Char,width,width, false),(blocks.get( 100 -player2.getBlock()).getCoordinates().centerX()-width/2),(blocks.get( 100 -player2.getBlock()).getCoordinates().centerY() - width/2), null);

        //Number the blocks
        for( int i = 0; i < 100; i++)
            canvas.drawText(((blocks.get(i).getValue()+ "")), blocks.get(i).getCoordinates().centerX(), blocks.get(i).getCoordinates().centerY(),paintFont);



    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public Matrix setObjects( int head, int tail, double length, int type){

        int x1,x2;
        int y1,y2;
        double snakeLength = length;
        double angle;

        x1 = blocks.get(100 - head).getCoordinates().centerX();
        x2 = blocks.get(100 - tail).getCoordinates().centerX();
        y1 = blocks.get(100 - head).getCoordinates().centerY();
        y2 = blocks.get(100 - tail).getCoordinates().centerY();

        angle = Math.toDegrees(Math.atan((double) (y1 - y2) / (x1 - x2)));
        Matrix transform = new Matrix();

        if (type == 1) {
            if(angle >= 0){
                transform.setTranslate((x1) + width*(float)(0.1), (y1)+ width*(float)(0.1));
                transform.preRotate(-90+(float)angle, width / 2, width / 2);
            }

            else{
                transform.setTranslate((x1) - width*(float)(0.5), (y1));
                transform.preRotate(-360-(float)angle, width / 2, width / 2);

            }
        }
        else{
            if(angle >= 0){
                transform.setTranslate((x1) + width*(float)(0.1), (y1)+ width*(float)(0.1));
                transform.preRotate(-90+(float)angle, width / 2, width / 2);
            }

            else{
                transform.setTranslate((x1) - width, (y1));
                transform.preRotate(+90+(float)angle, width / 2, width / 2);

            }
        }

        return transform;

    }

    public  double getObjectLength (int head, int tail){

        int x1,x2;
        int y1,y2;
        double snakeLength;
        int snakeWidth, snakeHeight;

        x1 = blocks.get(100 - head).getCoordinates().centerX();
        x2 = blocks.get(100 - tail).getCoordinates().centerX();
        y1 = blocks.get(100 - head).getCoordinates().centerY();
        y2 = blocks.get(100 - tail).getCoordinates().centerY();

        snakeWidth = x1 - x2;
        snakeHeight = y1 - y2;
        snakeLength = Math.sqrt((snakeHeight * snakeHeight) + (snakeWidth * snakeWidth));

        return snakeLength;

    }

    public ArrayList<Snake> getSnakes(){
        return snakes;
    }

    public ArrayList<Vine> getVines(){
        return vines;
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public int getItemWidth(){
        return width;
    }
}
