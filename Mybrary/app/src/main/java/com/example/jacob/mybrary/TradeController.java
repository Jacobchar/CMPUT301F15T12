package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Manages connection between trade UI and the trade model
 * Created by davidross on 2015-11-04
 */
public class TradeController {
    private ArrayList<Trade> tradeList = new ArrayList<>();
    private Trade currentTrade;
    private DataManager saver = DataManager.getInstance();

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
     * Get the currently loaded list of trades
     * @return the list of trades found
     */
    public ArrayList<Trade> getTradeList(){
        return this.tradeList;
    }
}
