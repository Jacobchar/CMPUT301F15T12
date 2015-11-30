package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import java.util.UUID;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 *  Interface linked to from tradeListActivity which shows a single trades details, allowing for edits
 *  Also allows accepting, or declining a trade
 */
public class ViewIndividualTradeActivity extends AppCompatActivity {
    AlertDialog alert;
    UUID currentTrade;
    TradeController controller = new TradeController();
    //ButtonController buttonHider = new ButtonController();

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
        controller.checkIfAccepted(currentTrade,this);
    }

    /**
     * Want the UI to update even if it isn't finished and recreated
     */
    @Override
    protected void onResume(){
        super.onResume();
        controller.getCurrentTradeOffer(this, currentTrade, (ListView) findViewById(R.id.yourItemsListView), (ListView) findViewById(R.id.theirItemsListView));
    }

    /**
     * Prompts a user to answer whether the trade is completed
     */
    public void bothUsersAcceptedPrompt(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Is this trade complete?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    controller.setTradeComplete(ViewIndividualTradeActivity.this,currentTrade);
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


    /**
     * Activates the modify trade button to allow trade editing
     * @param v view being acted on
     */
    public void modifyTradeButton(View v){
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, ProposeTradeActivity.class);
        intent.putExtra("currentTrade",currentTrade);
        startActivity(intent);
    }

    /**
     * Activates the accepted trade button
     * @param v view being acted on
     */
    public void acceptTradeButton(View v){
        controller.setAcceptedStatus(this, true, currentTrade, false);

        controller.startEmail(this,currentTrade);

    }

    /**
     * Activates the decline trade button
     * @param v view being acted on
     */
    public void declineTradeButton(View v){
        controller.setAcceptedStatus(this,false,currentTrade,false);
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
