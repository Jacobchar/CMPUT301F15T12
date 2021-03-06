package com.example.jacob.mybrary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * Gets friends list
 * Gets list of current trades
 * Created by davidross on 2015-11-04
 */
public class TradeController {
    final private DataManager saver = DataManager.getInstance();
    private LocalUser localUser = LocalUser.getInstance();
    private Activity localActivity = null;

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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return trade;
    }

    /**
     * Fetch the correct user's inventory to allow addition of new items to a trade
     *
     * @param currentTradeID current trade so correct users are looked at
     * @param parent         calling activity to update the UI
     * @param callingButton  allows differentiation between whether you are looking at your own, or another users inventory
     */
    public void getUserOfferSelections(final UUID currentTradeID, final Activity parent, final int callingButton) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    UUID userRequiredInventory;
                    if (callingButton == 1) {
                        userRequiredInventory = localUser.getUUID();
                    } else if (localUser.getUUID().equals(trade.getUser1UUID())) {
                        userRequiredInventory = trade.getUser2UUID();
                    } else {
                        userRequiredInventory = trade.getUser1UUID();
                    }
                    Inventory inv = saver.retrieveUser(userRequiredInventory.toString()).getInventory();
                    ArrayList<Book> bookList = inv.getBooks();

                    ListView view = (ListView) parent.findViewById(R.id.addBookToTradeListView);
                    ArrayAdapter<Book> adapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, bookList);

                    updateUI(view, adapter, parent);

                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                }
            }
        });
        t.start();
    }

    /**
     * Allows for books to be added and removed from a trade offer
     *
     * @param tradeID     ID for the trade currently being changed
     * @param book        book to be added or removed from the trade
     * @param offerToEdit a check to see which user's offer will be changed
     * @param addOffer    true to add a book, false to remove the book
     */
    public void changeOffer(final Activity parent, final UUID tradeID, final Book book, final int offerToEdit, final Boolean addOffer) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade currentTrade = saver.retrieveTrade(tradeID.toString());
                    saver.removeTrade(tradeID.toString());
                    List<Book> tradeOffer;
                    // Pressed 'change my offer' check if local user is user 1
                    if (offerToEdit == 1 && localUser.getUUID().equals(currentTrade.getUser1UUID())) {
                        tradeOffer = currentTrade.getUser1Offer();
                    }
                    // Pressed 'change my offer' local user must be user 2
                    else if (offerToEdit == 1) {
                        tradeOffer = currentTrade.getUser2Offer();
                    }
                    // Pressed 'change their offer' check if other user is user 1 or 2
                    else if (offerToEdit != 1 && localUser.getUUID().equals(currentTrade.getUser1UUID())) {
                        tradeOffer = currentTrade.getUser2Offer();
                    } else {
                        tradeOffer = currentTrade.getUser1Offer();
                    }
                    if (addOffer) {
                        tradeOffer.add(book);
                    } else {
                        tradeOffer.remove(book);
                    }
                    saver.storeTrade(currentTrade);
                    Thread.sleep(1500);

                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                } catch (InterruptedException e) {
                    Toast.makeText(parent, "View update interrupted, please reload the current screen", Toast.LENGTH_SHORT);
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
    public void setAcceptedStatus(final Activity parent, final Boolean status, final UUID currentTradeID, final Boolean setOtherUser) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    saver.removeTrade(currentTradeID.toString());

                    if (localUser.getUUID().equals(trade.getUser1UUID())) {
                        trade.setUser1Accepted(status);
                        if (setOtherUser) {
                            trade.setUser2Accepted(!status);
                        }
                    } else {
                        trade.setUser2Accepted(status);
                    }
                    if (setOtherUser) {
                        trade.setUser1Accepted(!status);
                    }

                    saver.storeTrade(trade);
                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                }

            }
        });
        t.start();
    }

    /**
     * Get the list of trades relevant to the local user
     *
     * @param parent              the calling activity
     * @param inProgressTradeView view which shows in progress trades
     * @param completedTradeView  view which shows the completed trades
     * @return the list of trades found
     */
    public void getTradeList(final Activity parent, final ListView inProgressTradeView, final ListView completedTradeView) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ArrayList<Trade> occuredTrades = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"user*ID\",\"query\":\"" + localUser.getUUID().toString() + "\"}}}");
                    ArrayList<Trade> completeTrades = new ArrayList<>();
                    for (Trade trade : occuredTrades) {
                        if (trade.isComplete()) {
                            completeTrades.add(trade);
                        }
                    }
                    for (Trade trade : completeTrades) {
                        occuredTrades.remove(trade);
                    }
                    ArrayAdapter<Trade> inProgressAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, occuredTrades);
                    updateUI(inProgressTradeView, inProgressAdapter, parent);
                    ArrayAdapter<Trade> completeAdapter = new ArrayAdapter<>(parent, R.layout.simple_list_item, completeTrades);
                    updateUI(completedTradeView, completeAdapter, parent);

                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
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
                    Thread.sleep(1500);
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);

                    if (trade.getUser1UUID().equals(localUser.getUUID())) {
                        fetchUserOffer(parent, trade.getUser1Offer(), yourItemsListView);
                        fetchUserOffer(parent, trade.getUser2Offer(), theirItemsListView);
                    } else {
                        fetchUserOffer(parent, trade.getUser2Offer(), yourItemsListView);
                        fetchUserOffer(parent, trade.getUser1Offer(), theirItemsListView);
                    }
                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                } catch (InterruptedException e) {
                    Toast.makeText(parent, "View update interrupted, please reload the current screen", Toast.LENGTH_SHORT);
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
     */
    public void deleteTrade(final Activity parent, final Trade trade) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    saver.removeTrade(trade.getTradeID().toString());
                    // Wait for the server to remove the trade
                    Thread.sleep(1000);
                    getTradeList(parent, (ListView) parent.findViewById(R.id.inProgressTradeListView), (ListView) parent.findViewById(R.id.completedTradesListView));
                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);

                } catch (InterruptedException e) {
                    Toast.makeText(parent, "View update interrupted, please reload the current screen", Toast.LENGTH_SHORT);
                }
            }
        }

        );
        t.start();
    }

    /**
     * Sets the selected trade to complete
     * Removes the trade from the server, and replaces it with the updated one
     *
     * @param currentTradeID trade to be completed
     */

    public void setTradeComplete(final Activity parent, final UUID currentTradeID) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + currentTradeID.toString() + "\"}}}").get(0);
                    saver.removeTrade(trade.getTradeID().toString());
                    trade.setIsComplete(true);
                    saver.storeTrade(trade);


                    User user1 = saver.retrieveUser(trade.getUser1UUID().toString());
                    user1.setSuccTrades(user1.getSuccTrades() + 1);
                    saver.removeUser(user1.getUUID().toString());
                    saver.storeUser(user1);

                    User user2 = saver.retrieveUser(trade.getUser2UUID().toString());
                    user2.setSuccTrades(user2.getSuccTrades() + 1);
                    saver.removeUser(user2.getUUID().toString());
                    saver.storeUser(user2);


                } catch (IOException e) {
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                }
            }
        }
        );
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
                    Toast.makeText(parent, "Error reading from file", Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    Toast.makeText(parent, "Error connecting to network", Toast.LENGTH_SHORT);
                }
            }
        }
        );
        t.start();
    }

    /**
     * Starts the send email async task
     * @param parent activity which is calling this method
     * @param tradeID UUID for the trade being looked at
     */
    public void startEmail(Activity parent, UUID tradeID){
        this.localActivity = parent;
        new emailHandler().execute(tradeID);
    }


    /**
     * Starts a second thread to handle the email app
     */
    private class emailHandler extends AsyncTask<UUID, Void, ArrayList<String>> {
        ArrayList<String> returnArray = new ArrayList<>();

        /**
         * fetch info from the server in the background
         * @param currentTradeID UUID for te trade being looked at
         * @return an arraylist containing the email of the other user in the trade, and the items being traded
         */
        @Override
        protected ArrayList<String> doInBackground(UUID... currentTradeID) {
            try {
                Trade trade = saver.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" +currentTradeID[0].toString()+ "\"}}}").get(0);
                UUID otherUser;
                if (trade.getUser1UUID().equals(localUser.getUUID())) {
                    otherUser = trade.getUser2UUID();
                } else {
                    otherUser = trade.getUser1UUID();
                }
                User user = saver.retrieveUser(otherUser.toString());
                String emailAddress = user.getEmailAddress();
                String tradeInfo = trade.getUser1Offer().toString() + "\n" + trade.getUser2Offer().toString();

                returnArray.add(emailAddress);
                returnArray.add(tradeInfo);


            } catch (IOException e) {

            } catch (JSONException e) {


            }
            returnArray.add("No email found");
            returnArray.add("No trade data found");
            return returnArray;
        }

        /**
         * Starts up the email app and fills in some of the fields, after the data has been fetched
         * @param returnVal values returned by the do in background method
         */
        @Override
        protected void onPostExecute(ArrayList<String> returnVal) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Trade Accepted");
            //http://stackoverflow.com/questions/9097080/intent-extra-email-not-populating-the-to-field
            // answered by MKJParekh
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { returnVal.get(0) });
            emailIntent.putExtra(Intent.EXTRA_TEXT,returnVal.get(1));
            localActivity.startActivity(emailIntent);
        }


    }
}
