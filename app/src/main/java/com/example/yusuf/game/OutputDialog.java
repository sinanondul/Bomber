package com.example.yusuf.game;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OutputDialog extends DialogFragment {

    String output = "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle message = getArguments();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setMessage(message.getString("MessageOut"));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // You don't have to do anything here if you just want it dismissed when clicked
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();

    }


}
