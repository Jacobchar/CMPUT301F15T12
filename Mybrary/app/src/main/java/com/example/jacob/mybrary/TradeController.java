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
    final private DataManager saver = DataManager.getInstance();
    private LocalUser localUser = LocalUser.getInstance();

    /**
     * Create a new trade between the current user, and the friend he is wanting to trade with
     *
     * @param user2 Friend with whom the trade is occurring
     */
    public Trade createNewTrade(User user2) {
        final Trade trade = new Trade(localUser, user2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    saver.storeTrade(trade);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return trade;
    }

    /**
     * Fetch the correct user's inventory to allow addition of new items to a trade
     * @param currentTradeID current trade so correct users are looked at
     * @param parent calling activity to update the UI
     * @param callingButton allows differentiation between whether you are looking at your own, or another users inventory
     */
    public void getUserOfferSelections(final UUID currentTradeID, final Activity parent, final int callingButton) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    UUID userRequiredInventory;
                    if(callingButton ==1){
                        userRequiredInventory = localUser.getUUID();
                    }
                    else if(localUser.getUUID().equals(trade.getUser1UUID())){
                        userRequiredInventory = trade.getUser2UUID();
                    }
                    else{
                        userRequiredInventory = trade.getUser1UUID();
                    }
                    Inventory inv = saver.retrieveUser(userRequiredInventory.toString()).getInventory();
                    ArrayList<Book> bookList =  inv.getBooks();

                    ListView view = (ListView) parent.findViewById(R.id.addBookToTradeListView);
                    ArrayAdapter<Book> adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, bookList);

                    updateUI(view, adapter, parent);

                } catch (IOException e) {

                } catch (JSONException e) {

                }
            }
        });
        t.start();
    }

    /**
     * Allows for books to be added and removed from a trade offer
     * @param tradeID ID for the trade currently being changed
     * @param book book to be added or removed from the trade
     * @param offerToEdit a check to see which user's offer will be changed
     * @param addOffer true to add a book, false to remove the book
     */
    public void changeOffer(final UUID tradeID, final Book book, final int offerToEdit, final Boolean addOffer){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Trade currentTrade = saver.retrieveTrade(tradeID.toString());
                    saver.removeTrade(tradeID.toString());
                    List<Book> tradeOffer;
                    // Pressed 'change my offer' check if local user is user 1
                    if(offerToEdit == 1 && localUser.getUUID().equals(currentTrade.getUser1UUID())){
                        tradeOffer = currentTrade.getUser1Offer();
                    }
                    // Pressed 'change my offer' local user must be user 2
                    else if(offerToEdit == 1){
                        tradeOffer = currentTrade.getUser2Offer();
                    }
                    // Pressed 'change their offer' check if other user is user 1 or 2
                    else if(offerToEdit != 1 && localUser.getUUID().equals(currentTrade.getUser1UUID())){
                        tradeOffer = currentTrade.getUser2Offer();
                    }
                    else{
                        tradeOffer = currentTrade.getUser1Offer();
                    }
                    if(addOffer){
                        tradeOffer.add(book);
                    }
                    else{
                        tradeOffer.remove(book);
                    }
                    saver.storeTrade(currentTrade);
                }
                catch(IOException e){

                }
                catch(JSONException e){

                }
            }
        });
        t.start();
    }

    /**
     * Set whether a user has accepted a given trade
     *
     * @param status         true or false for whether the user has accepted
     * @param currentTradeID the unique ID for the trade being altered
     */
    public void setAcceptedStatus(final Boolean status, final UUID currentTradeID) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    saver.removeTrade(currentTradeID.toString());

                    if (localUser.getUUID().equals(trade.getUser1UUID())) {
                        trade.setUser1Accepted(status);
                    } else {
                        trade.setUser2Accepted(status);
                    }
                    saver.storeTrade(trade);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

            }
        });
        t.start();
    }

    /**
     * Get the list of trades relevant to the local user
     *
     * @param parent the calling activity
     * @param inProgressTradeView  view which shows in progress trades
     * @param completedTradeView view which shows the completed trades
     * @return the list of trades found
     */
    public void getTradeList(final Activity parent,final ListView inProgressTradeView, final ListView completedTradeView) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ArrayList<Trade> occuredTrades = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"user*.myUUID\",\"query\":\"" + localUser.getUUID().toString() + "\"}}}");
                    ArrayList<Trade> completeTrades = new ArrayList<>();
                    for(Trade trade:occuredTrades){
                        if(trade.isComplete()){
                            completeTrades.add(trade);
                        }
                    }
                    for(Trade trade:completeTrades){
                        occuredTrades.remove(trade);
                    }
                    ArrayAdapter<Trade> inProgressAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, occuredTrades);
                    updateUI(inProgressTradeView, inProgressAdapter, parent);
                    ArrayAdapter<Trade> completeAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, completeTrades);
                    updateUI(completedTradeView, completeAdapter, parent);

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        t.start();
    }

    public void updateUI(final ListView view, final ArrayAdapter<?> adapter, Activity parent) {
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
     *
     * @param parent             the calling activity
     * @param currentTradeID     the UUID for the trade being examined
     * @param yourItemsListView  The view for the local user's trade offer
     * @param theirItemsListView the view for the other user's trade offer
     */
    public void getCurrentTradeOffer(final Activity parent, final UUID currentTradeID, final ListView yourItemsListView, final ListView theirItemsListView) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);

                    if (trade.getUser1UUID().equals(localUser.getUUID())) {
                        fetchUserOffer(parent, trade.getUser1Offer(), yourItemsListView);
                        fetchUserOffer(parent, trade.getUser2Offer(), theirItemsListView);
                    } else {
                        fetchUserOffer(parent, trade.getUser2Offer(), yourItemsListView);
                        fetchUserOffer(parent, trade.getUser1Offer(), theirItemsListView);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }

        });
        t.start();
    }

    /**
     * Helper function which ensures a trade offer exists, and adds it to the UI
     *
     * @param parent   activity which is calling the function
     * @param offer    a list of books being offered by the user
     * @param listView the view being updated with the book list
     */
    private void fetchUserOffer(Activity parent, List<Book> offer, ListView listView) {
        if (offer != null) {
            ArrayAdapter<Book> adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, offer);
            updateUI(listView, adapter, parent);
        }
    }

    /**
     * Removes a trade from the database and update the UI
     * NOTE: This runs slowly because the server needs to update before the UI updates
     *
     * @param trade the trade to be removed from the database
     *
     */
    public void deleteTrade(final Activity parent, final Trade trade) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    saver.removeTrade(trade.getTradeID().toString());
                    // Wait for the server to remove the trade
                    Thread.sleep(1500);
                    getTradeList(parent, (ListView) parent.findViewById(R.id.inProgressTradeListView),(ListView) parent.findViewById(R.id.completedTradesListView));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
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
     *
     * @param currentTradeID trade to be completed
     */
    public void setTradeComplete(final UUID currentTradeID) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    saver.removeTrade(trade.getTradeID().toString());
                    trade.setIsComplete(true);
                    saver.storeTrade(trade);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Check if both users have accepted the trade, then prompts if the trade is complete or not
     *
     * @param currentTradeID UUID for the trade currently being looked at
     * @param parent         the activity calling this method
     */
    public void checkIfAccepted(final UUID currentTradeID, final ViewIndividualTradeActivity parent) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    if (trade.getUser2Accepted() && trade.getUser1Accepted() && !trade.isComplete()) {
                        parent.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                parent.bothUsersAcceptedPrompt();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


}
