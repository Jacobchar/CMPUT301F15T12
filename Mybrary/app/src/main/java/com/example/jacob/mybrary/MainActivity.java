package com.example.jacob.mybrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Start Up and Main Menu activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DataManager.getInstance().loadLocalUser();
        } catch (IOException e) {
            Intent intent = new Intent(this, CreateUserActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Opens the Friends List Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openFriendsListActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);

    }

    /**
     * Opens the Settings Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openSettingsActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    /**
     * Opens the Inventory Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openInventoryActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the Trade List Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openTradeListActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, TradeListActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the View Profile Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openProfileActivity(View view) {
        try {
            DataManager.getInstance().loadLocalUser();
        }catch (IOException e){

        }
        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, ViewUserActivity.class);
        User user = LocalUser.getInstance();
        intent.putExtra("user", user);
        startActivity(intent);
    }

    /**
     * Opens the Top Traders Activity
     * @param view View required to be passed when called from the XML file
     */
    public void openTopTradersActivity(View view){
        Intent intent = new Intent(this, TopTradersActivity.class);
        startActivity(intent);
    }
}
