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
     * @param user One of the two users who is participating in the current trade
     * @param status true or false for whether the user has accepted
     * @param tradeID the unique ID for the trade being altered
     */
    public void setAcceptedStatus(User user, Boolean status, UUID tradeID){
        currentTrade = editExistingTrade(tradeID);
        if(user.getUUID().equals(currentTrade.getUser1UUID())){
            currentTrade.setUser1Accepted(status);
        }
        else if(user.getUUID().equals(currentTrade.getUser2UUID())){
            currentTrade.setUser2Accepted(status);
        }
    }

    /**
     * From the list of existing trades, find the matching trade to edit
     * @param tradeID UUID for the trade you are looking for
     * @return
     */
    public Trade editExistingTrade(UUID tradeID){
        for(Trade trade: tradeList){
            if(trade.getTradeID().equals(tradeID)){
                return trade;
            }
        }
        return null;
    }

    /**
     * Send a trade offer where user1 is the original trade initiator, and user 2 is the other party involved
     * @param user1Offer List of books user 1 wishes to offer
     * @param user2Offer List of books user 2 wishes to offer
     * @param tradeID the ID for the trade the offer belongs to
     */
    public void offerTrade(List<Book> user1Offer, List<Book> user2Offer, UUID tradeID){
        // Needs to send a new notification to the other person showing the new offer.
        currentTrade = editExistingTrade(tradeID);
    }

    /**
     * Get the list of trades relevant to the current user
     * @param parent the calling activity
     * @return the list of trades found
     */
    public ListView getTradeList(final Activity parent ) {
        final ListView tradeListView = (ListView) parent.findViewById(R.id.tradeListView);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ArrayList<Trade> occuredTrades = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"user*.myUUID\",\"query\":\""+localUser.getUUID().toString()+"\"}}}");
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
    // Todo: Make sure "your" trade offer shows up in the correct location
    public void getCurrentTradeOffer(final Activity parent, final UUID currentTradeID, final ListView yourItemsListView, final ListView theirItemsListView){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\""+currentTradeID.toString()+"\"}}}").get(0);

                    if(trade.getUser1UUID().equals(localUser.getUUID())){
                        ArrayAdapter<Book> yourAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, trade.getUser1Offer());
                        updateUI(yourItemsListView, yourAdapter, parent);

                        ArrayAdapter<Book> theirAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item,trade.getUser2Offer());
                        updateUI(theirItemsListView, theirAdapter, parent);
                    }

                    else{
                        ArrayAdapter<Book> yourAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, trade.getUser2Offer());
                        updateUI(yourItemsListView, yourAdapter, parent);

                        ArrayAdapter<Book> theirAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item,trade.getUser1Offer());
                        updateUI(theirItemsListView, theirAdapter, parent);
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
     * Removes a trade from the database and update the UI
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
     * Removes the trade from the server, and replaces it with th updated one
     * @param trade trade to be completed
     */
    public void setTradeComplete(Trade trade){
        trade.setIsComplete(true);
        try {
            saver.removeTrade(trade.getTradeID().toString());
            saver.storeTrade(trade);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}
