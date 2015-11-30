package com.example.jacob.mybrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
 * Interface linked to from a friends inventory, allowing a trade offer to be sent
 * This is also linked to from the modify trade button in an individual trade
 */
public class ProposeTradeActivity extends AppCompatActivity {
    UUID currentTrade;
    TradeController tradeController;
    ListView yourTradeOffer;
    ListView theirTradeOffer;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_trade);

        tradeController = new TradeController();

        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            currentTrade = (UUID) extras.getSerializable("currentTrade");
        }


    }

    /**
     * Update the interface every time it is opened
     */
    @Override
    protected void onResume(){
        super.onResume();

        yourTradeOffer = (ListView) findViewById(R.id.yourItemsListView);
        theirTradeOffer = (ListView) findViewById(R.id.theirItemsListView);
        tradeController.getCurrentTradeOffer(this,currentTrade,yourTradeOffer,theirTradeOffer);

        onClickListener(yourTradeOffer, currentTrade, 1);
        onClickListener(theirTradeOffer, currentTrade, 0);
    }

    /**
     * Disallow the back button being pressed to prevent placing of incomplete trades on the server
     */
    @Override
    public void onBackPressed(){
        Toast.makeText(this,"Please confirm your trade",Toast.LENGTH_SHORT).show();
    }
    /**
     * Listens for clicks to delete an item from a trade offer
     * @param view The view to watch for clicks
     * @param tradeID current trade being watched
     * @param callingView 1 for your offer, 0 for their offer
     */
    public void onClickListener(final ListView view, final UUID tradeID,final int callingView) {
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                final Book book = (Book) view.getItemAtPosition(pos);

                AlertDialog.Builder builder = new AlertDialog.Builder(ProposeTradeActivity.this);
                builder.setMessage("Remove book from offer?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tradeController.changeOffer(ProposeTradeActivity.this,tradeID, book, callingView, false);
                        yourTradeOffer = (ListView) findViewById(R.id.yourItemsListView);
                        theirTradeOffer = (ListView) findViewById(R.id.theirItemsListView);
                        tradeController.getCurrentTradeOffer(ProposeTradeActivity.this,currentTrade,yourTradeOffer,theirTradeOffer);
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

        });
    }

    /**
     * Activate the button which will send a new trade request
     * @param v view being acted on
     */
    public void sendRequestButton(View v){
        tradeController.setAcceptedStatus(this,true,currentTrade, true);
        Toast.makeText(this,"Trade offer sent",Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Activate the button allowing a user to add an item to their own trade offer
     * @param v view being acted upon
     */
    public void addYourOfferButton(View v){
        Intent intent = new Intent(ProposeTradeActivity.this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        intent.putExtra("addYourTrade",1);
        startActivity(intent);

    }

    /**
     * Activate the button allowing a user to add an item to their friend's inventory
     * @param v view being acted upon
     */
    public void addTheirOfferButton(View v){
        Intent intent = new Intent(ProposeTradeActivity.this, AddBookToTradeActivity.class);
        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        intent.putExtra("currentTrade", currentTrade);
        startActivity(intent);
    }



}
