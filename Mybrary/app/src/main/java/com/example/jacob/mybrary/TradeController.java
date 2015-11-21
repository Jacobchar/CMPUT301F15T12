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
    // Todo: Fix the query to fetch the correct user's trades
    public ListView getTradeList(final Activity parent ) {

        final ListView tradeListView = (ListView) parent.findViewById(R.id.tradeListView);
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ArrayList<Trade> occuredTrades = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"user1\",\"query\":\"testName2\"}}}");
                    Trade trade = new Trade(new User("Bob", "Email1", "12345", "Spirit","Likes Trades","Chicago"), new User("Harry","Email2", "123456","Male","Dislikes Trades", "Chicago"));
                    occuredTrades.add(trade);
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
     */
    // Todo: Make sure "your" trade offer shows up in the correct location
    public void getCurrentTradeOffer(final Activity parent, final UUID currentTradeID){
        final ListView yourTradeOffer = (ListView) parent.findViewById(R.id.yourItemsListView);
        final ListView theirTradeOffer = (ListView) parent.findViewById(R.id.theirItemsListView);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                    Book book = new Book("Name", 1, "", true);
                    ArrayList<Book> user1Offer = new ArrayList<>();
                    user1Offer.add(book);

                    ArrayAdapter<Book> yourAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, user1Offer);
                    updateUI(yourTradeOffer, yourAdapter, parent);

                    Book book1 = new Book("Name2", 1, "", true);
                    ArrayList<Book> user2Offer = new ArrayList<>();
                    user2Offer.add(book1);

                    ArrayAdapter<Book> theirAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, user2Offer);
                    updateUI(theirTradeOffer, theirAdapter, parent);
            }
        });
        t.start();
    }

    /**
     * Removes a trade from the database
     * @param parent The calling activity
     * @param trade the trade to be removed from the database
     */
    // Todo: Update to remove the deleted trade from the database
    public void deleteTrade(Activity parent, Trade trade){
        final ListView tradeListView = (ListView) parent.findViewById(R.id.tradeListView);
        ArrayList<Trade> occuredTrades = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, occuredTrades);
        tradeListView.setAdapter(adapter);


        adapter.notifyDataSetChanged();
    }


}
