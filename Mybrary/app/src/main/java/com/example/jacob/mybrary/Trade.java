package com.example.jacob.mybrary;

import java.util.List;
import java.util.UUID;

/**
 * Stores data for trades
 * Stores users involved, their trade offers, accepted status, completion status, and a trade ID
 *  Created by davidross on 2015-11-02.
 */

public class Trade {
    private User user1;
    private User user2;
    private List<Book> user1Offer;
    private List<Book> user2Offer;
    private Boolean user1Accepted = false;
    private Boolean user2Accepted = false;
    private Boolean isComplete = false;
    private UUID tradeID;

    /**
     * Trade between two users
     * @param user1 One user who is taking part of the trade
     * @param user2 A second user who is taking part in the trade
     */
    public Trade(User user1, User user2){
        tradeID = tradeID.randomUUID();
        this.user1 = user1;
        this.user2 = user2;
    }
    /**
     * Set whether user 1 has accepted the current trade offer
     * @param accepted True or False as to whether the trade has been accepted
     */
    public void setUser1Accepted(Boolean accepted){
        user1Accepted = accepted;
    }

    /**
     * Set whether user 2 has accepted the current trade offer
     * @param accepted True of False as to whether the trade has been accepted
     */
    public void setUser2Accepted(Boolean accepted){
        user2Accepted = accepted;
    }

    /**
     * Get the accept status for the current trade offer
     * @return Whether user 1 has accepted the current trade
     */
    public Boolean getUser1Accepted(){
        return this.user1Accepted;
    }

    /**
     * Get the accept status for the current trade offer
     * @return Whether user 2 has accepted the current trade
     */
    public Boolean getUser2Accepted(){
        return this.user2Accepted;
    }

    /**
     * Override toString so the views can print the correct information
     * @return String containing trade info
     */
    @Override
    public String toString(){
        if(isComplete()){
            return user1.getName() +  " offers " + getUser1Offer() + " to " + user2.getName() + " for " + getUser2Offer() + "\n"+"Trade Complete";
        }
        else{
            return user1.getName() +  " offers " + getUser1Offer() + " to " + user2.getName() + " for " + getUser2Offer() + "\n"+" Trade Incomplete";
        }

    }

    /**
     * Get the unique identifier for user 1
     * @return UUID for user 1
     */
    public UUID getUser1UUID(){
        return this.user1.getUUID();
    }

    /**
     * Get the unique identifier for user 2
     * @return UUID for user 2
     */
    public UUID getUser2UUID(){
        return this.user2.getUUID();
    }

    /**
     * Get the unique identifier for a trade
     * @return UUID for the trade
     */
    public UUID getTradeID(){
        return this.tradeID;
    }

    /**
     * Get the offer that user 1 has currently
     * @return User1's offer
     */
    public List<Book> getUser1Offer() {
        return user1Offer;
    }

    /**
     *  The offer user 1 will send
     * @param user1Offer a list of books user 1 will offer
     */
    public void setUser1Offer(List<Book> user1Offer) {
        this.user1Offer = user1Offer;
    }
    /**
     * Get the offer that user 2 has currently
     * @return User2's offer
     */
    public List<Book> getUser2Offer() {
        return user2Offer;
    }

    /**
     *  The offer that user 2 will send
     * @param user2Offer a list of books user 2 will offer
     */
    public void setUser2Offer(List<Book> user2Offer) {
        this.user2Offer = user2Offer;
    }

    /**
     * Get the 'is complete' flag
     * @return whether the trade is complete
     */
    public Boolean isComplete() {
        return isComplete;
    }

    /**
     * Set the 'is complete' flag
     * @param isComplete set whether the trade is complete or not. (true/false)
     */
    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
    }
}
