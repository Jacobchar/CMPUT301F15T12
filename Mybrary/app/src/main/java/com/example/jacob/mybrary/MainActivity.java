package com.example.jacob.mybrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFriendsListActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, FriendsListActivity.class);
        startActivity(intent);

    }

    public void openSettingsActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }


    public void openInventoryActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);

    }

    public void openTradeListActivity(View view) {

        // note: FromActivity.class, ToActivity.class
        Intent intent = new Intent(this, TradeListActivity.class);
        startActivity(intent);

    }

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

    public void openTopTradersActivity(View view){
        Intent intent = new Intent(this, TopTradersActivity.class);
        startActivity(intent);
    }
}
