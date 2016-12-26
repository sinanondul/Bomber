package com.example.yusuf.game;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameManager extends AppCompatActivity {

    RelativeLayout layout; // XML Layout which lays the board and the game panel
    Button btnDice; //Android Button
    Board gameBoard;    //Instance of Board that will be displayed
    Dice dice;  // Instance of Dice object
    int roll = 0, animatePos;
    boolean turn = false, charSelection; // Boolean variable which indicates whose turn it is to move
    Player player1, player2; //Playes intialised
    int type = 1; // Type of game where 1 is single player with computer and 2 is multiplayer vs another person
    int playerAnimate;
    Handler handler = new Handler();
    DialogFragment dialog;
    Bundle message;
    TextView txtPlayer1, txtPlayer2;
    ImageView imgPlayer1, imgPlayer2, imgChar1, imgChar2;
    Intent in;
    String player1Name, player2Name;


    @Override
    // This is like the constructor of the class and is called when the Activity is started
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent passed = getIntent();
        String modeVal = passed.getStringExtra("Mode");


        type = Integer.parseInt(modeVal);
        player1Name = passed.getStringExtra("CharName").toString();
        player2Name = passed.getStringExtra("CharName2").toString();
        charSelection = passed.getBooleanExtra("Gender", true);

        player1Name = player1Name.substring(0, 1).toUpperCase() + player1Name.substring(1);
        player2Name = player2Name.substring(0, 1).toUpperCase() + player2Name.substring(1);



        // Initialises the dice, players and game board according to the parameters passed from the previous Activity where user defines his character properties
        dice = new Dice();
        player1 = new Player(player1Name, charSelection, this, 1);
        player2 = new Player(player2Name, !charSelection, this, 2);

        // Toast allows system to display message to user and is an Android feature
        final Toast toast = Toast.makeText(this, "Computer's Turn", Toast.LENGTH_SHORT);


        in = new Intent(GameManager.this, DiceDisplay.class);


        // Game Board is intialised
        gameBoard = new Board(this, player1, player2, 1);

        // Layout is set
        layout = (RelativeLayout) View.inflate(this, R.layout.panel, null);
        layout.addView(gameBoard);
        setContentView(layout);

        imgPlayer1 = (ImageView) findViewById(R.id.imgPlayer1);
        imgPlayer2 = (ImageView) findViewById(R.id.imgPlayer2);
        txtPlayer1 = (TextView) findViewById(R.id.txtPlayer1);
        txtPlayer2 = (TextView) findViewById(R.id.txtPlayer2);
        imgChar1 = (ImageView) findViewById(R.id.imgChar1);
        imgChar2 = (ImageView) findViewById(R.id.imgChar2);

        txtPlayer1.setText(player1.getName());
        txtPlayer2.setText(player2.getName());
        imgPlayer1.setImageResource(R.drawable.empty);
        imgPlayer2.setImageResource(R.drawable.empty);

        if (player1.getGender()) {
            imgChar1.setImageResource(R.drawable.male);
            imgChar2.setImageResource(R.drawable.female);
        } else {
            imgChar1.setImageResource(R.drawable.female);
            imgChar2.setImageResource(R.drawable.male);
        }

        btnDice = (Button) findViewById(R.id.btnDice);
        TextView txtFill = (TextView) findViewById(R.id.txtFill);

        //OnClickListener of the button is defined in the onCreate method which acts as a constructor fot this class
        btnDice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // On the click of the dice button a random number from 1 to 6 is returned by the dice objec tand the system checks which player to move
                int pos;


                if (type == 2) {
                    roll = dice.getNum();
                    Toast.makeText(getApplicationContext(), "Rolled " + roll + "!", Toast.LENGTH_SHORT).show();

                    Log.d("ValuePut", roll + "");
                    in.putExtra("Rolled", roll);
                    startActivity(in);

                    turn = !turn;
                    if (turn) {

                        gameBoard.getPlayer1().updatePos(roll);
                        txtPlayer1.setShadowLayer(0f, 0, 0, Color.WHITE);
                        txtPlayer1.setTextColor(Color.parseColor("#C4C4C4"));
                        txtPlayer2.setTextColor(Color.WHITE);
                        txtPlayer2.setShadowLayer(1.5f, -1, -1, Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                gameBoard.invalidate();
                            }
                        }, 1000);

                        checkPosition();
                        checkWinner();
                        checkItem(gameBoard.getPlayer1());
                    } else {
                        gameBoard.getPlayer2().updatePos(roll);

                        txtPlayer2.setShadowLayer(0f, 0, 0, Color.WHITE);
                        txtPlayer2.setTextColor(Color.parseColor("#C4C4C4"));
                        txtPlayer1.setTextColor(Color.WHITE);
                        txtPlayer1.setShadowLayer(1.5f, -1, -1, Color.GREEN);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {

                                gameBoard.invalidate();
                            }
                        }, 1000);

                        checkPosition();
                        checkWinner();
                        checkItem(gameBoard.getPlayer2());
                    }

                    checkPosition();
                    checkWinner();

                    if (turn)
                        checkItem(gameBoard.getPlayer2());
                    else
                        checkItem(gameBoard.getPlayer1());

                } else {

                    roll = dice.getNum();
                    Toast.makeText(getApplicationContext(), "Rolled " + roll + "!", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(GameManager.this, DiceDisplay.class);
                    in.putExtra("Rolled", "4");
                    startActivity(in);

                    turn = !turn;
                    txtPlayer2.setShadowLayer(0f, 0, 0, Color.WHITE);
                    txtPlayer2.setTextColor(Color.parseColor("#C4C4C4"));
                    txtPlayer1.setTextColor(Color.WHITE);
                    txtPlayer1.setShadowLayer(1.5f, -1, -1, Color.GREEN);

                    gameBoard.getPlayer1().updatePos(roll);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            gameBoard.invalidate();
                        }
                    }, 1000);

                    checkPosition();
                    checkWinner();
                    checkItem(gameBoard.getPlayer1());
                    btnDice.setEnabled(false);

                    toast.show();
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            int pos;
                            roll = dice.getNum();
                            Toast.makeText(getApplicationContext(), "Rolled " + roll + "!", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(GameManager.this, DiceDisplay.class);
                            in.putExtra("Rolled", roll);
                            startActivity(in);
                            turn = !turn;
                            txtPlayer2.setShadowLayer(0f, 0, 0, Color.WHITE);
                            txtPlayer2.setTextColor(Color.parseColor("#C4C4C4"));
                            txtPlayer1.setTextColor(Color.WHITE);
                            txtPlayer1.setShadowLayer(1.5f, -1, -1, Color.GREEN);
                            //animatePos = gameBoard.getPlayer2().getBlock();
                            gameBoard.getPlayer2().updatePos(roll);


                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    gameBoard.invalidate();
                                }
                            }, 1000);

                            checkPosition();
                            checkWinner();
                            checkItem(gameBoard.getPlayer2());
                            btnDice.setEnabled(true);
                        }
                    };

                    Handler h = new Handler();
                    h.postDelayed(r, 2000);

                }

            }

        });

    }

    // This method checks the position fo the players and updates them if necessary
    public void checkPosition() {
        if (gameBoard.getPlayer1().getBlock() == gameBoard.getPlayer2().getBlock()) {
            if (turn) {
                gameBoard.getPlayer2().updatePos(-3);
                dispMessage(player1.getName() + " beat " + player2.getName() + " back 3 steps");
            } else {
                gameBoard.getPlayer1().updatePos(-3);
                dispMessage(player2.getName() + " beat " + player1.getName() + " back 3 steps");
            }
        }
    }

    public boolean checkItem(Player player) {

        ArrayList<Snake> snakes = gameBoard.getSnakes();
        ArrayList<Vine> vines = gameBoard.getVines();
        ArrayList<Item> items = gameBoard.getItems();
        Log.d("checkItem", player.getName() + " holds " + player.getItemType());

        // Checking if player lands on Snake
        for (int i = 0; i < snakes.size(); i++) {
            if (player.getBlock() == snakes.get(i).getHead()) {
                Log.d("checkItem", player.getName() + " Has landed on snake");
                // Checks if player has antidote
                if (player.getItemType() != 2) {
                    Log.d("checkItem", player.getName() + " doesnt have Anti-venom: " + player.getItemType());
                    player.setPos(snakes.get(i).getTail());
                    gameBoard.invalidate();
                    dispMessage(player.getName() + " bitten by snake and fell to block " + snakes.get(i).getTail());
                } else {
                    Log.d("checkItem", player.getName() + " has Anti-venom: " + player.getItemType());
                    player.setItemType(0);
                    changeItem(player.getPlayerNum(), 0);
                    dispMessage(player.getName() + " bitten by snake but " + player.getName() + " used anti-venom.");
                }
                checkPosition();
                return true;
            }
        }

        // Checking if player lands on Vine
        for (int i = 0; i < vines.size(); i++) {
            if (player.getBlock() == vines.get(i).getHead()) {

                Log.d("checkItem", player.getName() + " Has landed on vine");
                // Checks if player has oil
                if (player.getItemType() != 3) {
                    Log.d("checkItem", player.getName() + " doesnt have Oil: " + player.getItemType());
                    player.setPos(vines.get(i).getTail());
                    gameBoard.invalidate();
                    dispMessage(player.getName() + " used vine to swing to block " + vines.get(i).getTail());
                } else {
                    Log.d("checkItem", player.getName() + " has Oil: " + player.getItemType());
                    player.setItemType(0);
                    changeItem(player.getPlayerNum(), 0);
                    dispMessage(player.getName() + " tried to swing on vine but " + player.getName() + " is covered in oil.");
                }
                checkPosition();
                return true;
            }

        }

        // Checking if player lands on Item
        for (int i = 0; i < items.size(); i++) {
            if ((player.getBlock() == items.get(i).getBlockPos()) && (items.get(i).getIsVisible())) {

                if (items.get(i).getItemType() == 1) {
                    int banana = getBanana();
                    player.updatePos(banana);
                    if (banana > 0)
                        dispMessage(player.getName() + " slipped on banana peal and moved foreward " + banana + " steps!");
                    else if (banana < 0)
                        dispMessage(player.getName() + " slipped on banana peal and moved backwards " + banana + " steps!");
                    else
                        dispMessage(player.getName() + " slipped on banana but managed not to slip");
                    items.get(i).setIsvisible(false);
                    checkItem(player);
                    checkPosition();
                }

                // Checks if player holds item
                else if (player.getItemType() == 0) {
                    player.setItemType(items.get(i).getItemType());
                    changeItem(player.getPlayerNum(), items.get(i).getItemType());
                    Log.d("checkItem", player.getName() + " now holds " + player.getItemType() + " " + items.get(i).getItemType());
                    dispMessage(player.getName() + getMessage(items.get(i).getItemType()));
                    items.get(i).setIsvisible(false);
                } else {
                    dispMessage(player.getName() + getMessage(items.get(i).getItemType()) + " but already holds item");
                }

                gameBoard.invalidate();
                return true;
            }
        }
        return false;
    }

    public String getMessage(int item) {

        if (item == 3) {
            return " fell in oil";
        } else
            return " found anti-venom";
    }

    public int getBanana() {
        Random rand = new Random();
        return rand.nextInt(3) + -3;
    }

    public void dispMessage(String out) {

        dialog = new OutputDialog();
        message = new Bundle();
        message.putString("MessageOut", out);
        dialog.setArguments(message);
        dialog.show(getSupportFragmentManager(), "OutputMessage");
    }

    public void dispFinish(String out) {

        dialog = new FinishDialog();
        message = new Bundle();
        message.putString("MessageOut", out);
        dialog.setArguments(message);
        dialog.show(getSupportFragmentManager(), "OutputMessage");
    }

    public void checkWinner() {

        if (player1.getBlock() == 100) {
            dispFinish(player1.getName() + " has survived the jungle!");
        } else if (player2.getBlock() == 100) {
            dispFinish(player2.getName() + " has survived the jungle!");
        }
    }

    public void changeItem(int player, int item) {
        if (player == 1) {

            switch (item) {
                case 2:
                    imgPlayer1.setImageResource(R.drawable.beaker);
                    break;
                case 3:
                    imgPlayer1.setImageResource(R.drawable.oil);
                    break;
                default:
                    imgPlayer1.setImageResource(R.drawable.empty);
            }
        } else {
            switch (item) {
                case 2:
                    imgPlayer2.setImageResource(R.drawable.beaker);
                    break;
                case 3:
                    imgPlayer2.setImageResource(R.drawable.oil);
                    break;
                default:
                    imgPlayer2.setImageResource(R.drawable.empty);
            }
        }
    }
}
