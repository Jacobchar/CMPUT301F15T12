package com.example.jacob.mybrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
 * Allows users to see the books they are allowed to choose as a trade offer
 */
public class AddBookToTradeActivity extends AppCompatActivity {
    UUID currentTrade;
    TradeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_to_trade);

        controller = new TradeController();

        // http://stackoverflow.com/questions/2965109/passing-data-between-activities-in-android
        // Answered by Pentium10
        final Bundle extras = getIntent().getExtras();
        int callingButton = getIntent().getIntExtra("addYourTrade", 0);
        if (extras != null) {
            currentTrade = (UUID) extras.getSerializable("currentTrade");
        }

        controller.getUserOfferSelections(currentTrade, this, callingButton);
        onClickListener((ListView) findViewById(R.id.addBookToTradeListView), currentTrade, callingButton);
    }

    /**
     * Listen to clicks on the view
     * @param view view being listened to
     * @param tradeID trade that is having items added to it
     * @param offerToEdit
     */
    public void onClickListener(final ListView view, final UUID tradeID, final int offerToEdit) {
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {

                final Book book = (Book) view.getItemAtPosition(pos);
                controller.changeOffer(AddBookToTradeActivity.this,tradeID, book, offerToEdit,true);
                finish();
            }

        });
        }
    }
