package com.example.jacob.mybrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.UUID;

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
