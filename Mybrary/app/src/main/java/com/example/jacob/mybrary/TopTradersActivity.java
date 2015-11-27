package com.example.jacob.mybrary;

import java.util.ArrayList;

import android.app.Activity;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;

        import java.io.IOException;
        import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class TopTradersActivity extends AppCompatActivity {
    ArrayList<User> topList;
    DataManager dataManager = DataManager.getInstance();
    private ListView listView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_traders);
        fillList(false);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tradersToggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    fillList(true);
                }
                else{
                    fillList(false);
                }
            }
        });
    }


    public void makeList(){
        ArrayList<User> friends = LocalUser.getInstance().getFriendsList().getUsers();
        Collections.sort(friends);
        for (int i=0; i<10; i++){
            if (!friends.isEmpty()){
                topList.add(friends.get(i));
            }
        }
    }

    //Testing
    public void fillList(Boolean state){
        listView = (ListView) findViewById(R.id.topTradersListView);
        //ArrayList<String> names = new ArrayList<>();
        //for (int i = 0; i < topList.size(); i++){
        //    names.add(topList.get(i).getName());
        //}

        if (state) {
            String names[] = new String[]{"Dominieque 2", "Jackylnn 1", "Victor 1", "Betty 0", "Daphne 0"};
            listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, names);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
        else{
            String names[] = new String[]{"Doc 45" };
            listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, names);
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
    }
}