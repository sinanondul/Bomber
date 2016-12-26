package com.example.yusuf.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class CharacterSelect extends AppCompatActivity {
    ImageButton male;
    ImageButton female;
    boolean character = true;
    String charName = "Player";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        male = (ImageButton)findViewById(R.id.imgMale);
        female = (ImageButton)findViewById(R.id.imgFemale);
        final EditText nameInput = (EditText)findViewById(R.id.txtName);
        Button start = (Button)findViewById(R.id.btnPass);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                male.setImageResource(R.drawable.male2sel);
                female.setImageResource(R.drawable.female);
                character = true;
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                male.setImageResource(R.drawable.male2);
                female.setImageResource(R.drawable.femalesel);
                character = false;
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                charName = nameInput.getText().toString();

                Intent start = new Intent(CharacterSelect.this, GameManager.class);

                start.putExtra("CharName", charName);
                start.putExtra("CharName2", "Computer");
                start.putExtra("Mode", "1");
                start.putExtra("Gender", character);

                startActivity(start);
            }
        });


    }
}
