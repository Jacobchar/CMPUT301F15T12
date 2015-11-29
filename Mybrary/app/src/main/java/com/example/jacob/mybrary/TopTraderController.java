package com.example.jacob.mybrary;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Calculates top traders based off number of completed trades a user has
 * Created by davidross on 2015-11-29.
 */
public class TopTraderController {
    final private DataManager saver = DataManager.getInstance();
    final private User localUser = LocalUser.getInstance();

    /**
     * Updates the top traders list view with the required information
     * @param parent Activity who is calling the method
     * @param showFriendsOnly whether only top friends should be shown, or all users
     */
    public void fillView(final Activity parent, final Boolean showFriendsOnly){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ListView listView = (ListView) parent.findViewById(R.id.topTradersListView);

                    ArrayList<User> topTraders;
                    if(showFriendsOnly){
                        topTraders = getTopFriends();
                    }
                    else{
                        topTraders = getTopUsers();
                    }

                    ArrayAdapter<User> listAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, topTraders);
                    new TradeController().updateUI(listView, listAdapter, parent);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Gets a list of the top friends sorted by the number of complete trades they have
     * @return a list of friends sorted by number of complete trades
     * @throws IOException Error accessing the server, or files
     * @throws JSONException Error accessing files
     */
   private ArrayList<User> getTopFriends() throws IOException, JSONException {
       ArrayList<User> friendsList = saver.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"myUUID\",\"query\":\"" + localUser.getUUID().toString() + "\"}}}").get(0).getFriendsList().getUsers();
       Collections.sort(friendsList);
       return friendsList;
   }


    /**
     * Gets a list of the top users sorted by the number of complete trades they have
     * @return a list of users sorted by number of complete trades
     * @throws IOException Error accessing the server, or files
     * @throws JSONException Error accessing files
     */
    private ArrayList<User> getTopUsers() throws IOException, JSONException{
        ArrayList<User> userList = saver.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"*\",\"query\":\"*\"}}}");
        Collections.sort(userList);
        return userList;
    }
}
