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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.UUID;

/**
 *  Interface linked to from tradeListActivity which shows a single trades details, allowing for edits
 *  Also allows accepting, or declining a trade
 */
public class ViewIndividualTradeActivity extends AppCompatActivity {
    AlertDialog alert;
    UUID currentTrade;
    TradeController controller = new TradeController();

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

    public void bothUsersAcceptedPrompt(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Is this trade complete?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    controller.setTradeComplete(currentTrade);
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


    public void modifyTradeButton(View v){
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, ProposeTradeActivity.class);
        intent.putExtra("currentTrade",currentTrade);
        startActivity(intent);
    }

    public void acceptTradeButton(View v){
        controller.setAcceptedStatus(true, currentTrade,false);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        controller.sendAcceptedEmail(emailIntent, currentTrade);
        startActivity(Intent.createChooser(emailIntent, "Send Trade Info"));

    }

    public void declineTradeButton(View v){
        controller.setAcceptedStatus(false,currentTrade,false);
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
