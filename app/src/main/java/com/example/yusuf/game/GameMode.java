package com.example.yusuf.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class GameMode extends AppCompatActivity {
    Intent start;
    RadioGroup rdoGroup;
    RadioButton btnMulti, btnSingle;
    Button btnCon;
    int mode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        rdoGroup = (RadioGroup)findViewById(R.id.rdoGroup);
        btnMulti = (RadioButton)findViewById(R.id.rdoMulti);
        btnSingle = (RadioButton)findViewById(R.id.rdoSingle);
        btnCon = (Button)findViewById(R.id.btnConfirm);

        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rdoMulti)
                    mode = 2;
                else
                    mode = 1;
            }
        });

        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mode == 1)
                   start = new Intent(GameMode.this, CharacterSelect.class);
                 else
                    start = new Intent(GameMode.this, MultiCharSelect.class);

                startActivity(start);
            }
        });

    }
}
