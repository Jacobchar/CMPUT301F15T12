package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.UUID;

/**
 *  Interface linked to from tradeListActivity which shows a single trades details, allowing for edits
 *  Also allows accepting, or declining a trade
 */
public class ViewIndividualTradeActivity extends AppCompatActivity {
    AlertDialog alert;
    UUID currentTrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_trade);

        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            currentTrade = (UUID) extras.getSerializable("currentTrade");
        }

    }

    public void modifyTradeButton(View v){
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, ProposeTradeActivity.class);
        startActivity(intent);
    }

    public void acceptTradeButton(View v){

    }

    public void declineTradeButton(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewIndividualTradeActivity.this);
        builder.setMessage("Would you like to create a counter offer?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ViewIndividualTradeActivity.this, ProposeTradeActivity.class);
                intent.putExtra("currentTrade",currentTrade);
                startActivity(intent);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });


        alert = builder.create();
        alert.show();

    }

    public AlertDialog getAlertDialog(){
        return this.alert;
    }

}
