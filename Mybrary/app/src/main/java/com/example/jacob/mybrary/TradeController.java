package com.example.jacob.mybrary;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gets friends list
 * Gets list of current trades
 * Created by davidross on 2015-11-04
 */
public class TradeController {
    private ArrayList<Trade> tradeList = new ArrayList<>();
    private Trade currentTrade;
    final private DataManager saver = DataManager.getInstance();
    private LocalUser localUser = LocalUser.getInstance();
    final  private ConnectionManager connection = ConnectionManager.getInstance();

    /**
     * Create a new trade between the current user, and the friend he is wanting to trade with
     * @param user1 Current user, who is initiating the trade
     * @param user2 Friend with whom the trade is occurring
     */
    public void createNewTrade(User user1, User user2){
        currentTrade = new Trade(user1,user2);
        tradeList.add(currentTrade);
    }

    /**
     * Set whether a user has accepted a given trade
     * @param status true or false for whether the user has accepted
     * @param currentTradeID the unique ID for the trade being altered
     */
    public void setAcceptedStatus(final Boolean status,final UUID currentTradeID){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\""+currentTradeID.toString()+"\"}}}").get(0);
                    saver.removeTrade(currentTradeID.toString());

                    if(localUser.getUUID().equals(trade.getUser1UUID())){
                        trade.setUser1Accepted(status);
                    }
                    else{
                        trade.setUser2Accepted(status);
                    }
                    saver.storeTrade(trade);
                }
                catch(IOException e){
                    e.printStackTrace();
                    return;
                }
                catch(JSONException e){
                    e.printStackTrace();
                    return;
                }

            }
        });
        t.start();
    }

    /**
     * Get the list of trades relevant to the local user
     * @param parent the calling activity
     * @return the list of trades found
     */
    public ListView getTradeList(final Activity parent ) {
        final ListView tradeListView = (ListView) parent.findViewById(R.id.tradeListView);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ArrayList<Trade> occuredTrades = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"user*.myUUID\",\"query\":\"" + localUser.getUUID().toString() + "\"}}}");
                    final  ArrayAdapter<Trade> adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, occuredTrades);
                    updateUI(tradeListView, adapter, parent);
                }
                catch(IOException e){
                    e.printStackTrace();
                    return;
                }
                catch(JSONException e){
                    e.printStackTrace();
                    return;
                }
            }
        });
        t.start();
        return tradeListView;
    }

    public void updateUI(final ListView view, final  ArrayAdapter<?> adapter, Activity parent){
        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Gets the current offers for the trade being looked at
     * @param parent the calling activity
     * @param currentTradeID the UUID for the trade being examined
     * @param yourItemsListView The view for the local user's trade offer
     * @param theirItemsListView  the view for the other user's trade offer
     */
    public void getCurrentTradeOffer(final Activity parent, final UUID currentTradeID, final ListView yourItemsListView, final ListView theirItemsListView){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\""+currentTradeID.toString()+"\"}}}").get(0);

                    if(trade.getUser1UUID().equals(localUser.getUUID())){
                        fetchUserOffer(parent, trade.getUser1Offer(),yourItemsListView);
                        fetchUserOffer(parent, trade.getUser2Offer(), theirItemsListView);
                    }

                    else{
                        fetchUserOffer(parent, trade.getUser2Offer(),yourItemsListView);
                        fetchUserOffer(parent, trade.getUser1Offer(),theirItemsListView);
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                    return;
                }
                catch(JSONException e){
                    e.printStackTrace();
                    return;
                }
            }

        });
        t.start();
    }

    /**
     * Helper function which ensures a trade offer exists, and adds it to the UI
     * @param parent activity which is calling the function
     * @param offer a list of books being offered by the user
     * @param listView the view being updated with the book list
     */
    private void fetchUserOffer(Activity parent,List<Book> offer, ListView listView){
        if(offer != null){
            ArrayAdapter<Book> adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, offer);
            updateUI(listView, adapter, parent);
        }
    }

    /**
     * Removes a trade from the database and update the UI
     * NOTE: This runs slowly because the server needs to update before the UI updates
     * @param trade the trade to be removed from the database
     */
    public void deleteTrade(final Activity parent,final Trade trade){
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    saver.removeTrade(trade.getTradeID().toString());
                    // Wait for the server to remove the trade
                    Thread.sleep(1500);
                    getTradeList(parent);
                }
                catch(IOException e){
                    e.printStackTrace();
                    return;
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                    return;
                }
            }
        });
        t.start();

    }

    /**
     * Sets the selected trade to complete
     * Removes the trade from the server, and replaces it with the updated one
     * @param currentTradeID trade to be completed
     */
    public void setTradeComplete(final UUID currentTradeID){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\""+currentTradeID.toString()+"\"}}}").get(0);
                    saver.removeTrade(trade.getTradeID().toString());
                    trade.setIsComplete(true);
                    saver.storeTrade(trade);
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

    public void checkIfAccepted(final UUID currentTradeID, final ViewIndividualTradeActivity parent){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\""+currentTradeID.toString()+"\"}}}").get(0);
                    if(trade.getUser2Accepted() && trade.getUser1Accepted() && !trade.isComplete()){
                        parent.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                parent.bothUsersAcceptedPrompt();
                            }
                        });
                    }

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


}
