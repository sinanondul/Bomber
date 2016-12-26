package com.example.yusuf.game;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class FinishDialog extends DialogFragment {

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
               startActivity( new Intent(getActivity(), MainStart.class));   // You don't have to do anything here if you just want it dismissed when clicked
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();

    }


}
