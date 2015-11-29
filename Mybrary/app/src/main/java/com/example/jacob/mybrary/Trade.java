package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Stores data for trades
 * Stores users involved, their trade offers, accepted status, completion status, and a trade ID
 *  Created by davidross on 2015-11-02.
 */

public class Trade {
    private String user1name;
    private String user2name;
    private UUID user1ID;
    private UUID user2ID;
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
        tradeID = UUID.randomUUID();
        this.user1Offer = new ArrayList<>();
        this.user2Offer = new ArrayList<>();
        this.user1name = user1.getName();
        this.user2name = user2.getName();
        this.user1ID = user1.getUUID();
        this.user2ID = user2.getUUID();
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
            return user1name + "'s trade with " + user2name + "\n"+"Complete";
        }
        else{
            return user1name + "'s trade with " + user2name + "\n" +
                    user1name + " has accepted: "+ getUser1Accepted().toString() +"\n"+
                    user2name + " has accepted: "+ getUser2Accepted().toString() +"\n"+
                    "In Progress";
        }

    }

    /**
     * Get the unique identifier for user 1
     * @return UUID for user 1
     */
    public UUID getUser1UUID(){
        return this.user1ID;
    }

    /**
     * Get the unique identifier for user 2
     * @return UUID for user 2
     */
    public UUID getUser2UUID(){
        return this.user2ID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (user1name != null ? !user1name.equals(trade.user1name) : trade.user1name != null)
            return false;
        if (user2name != null ? !user2name.equals(trade.user2name) : trade.user2name != null)
            return false;
        if (user1ID != null ? !user1ID.equals(trade.user1ID) : trade.user1ID != null) return false;
        if (user2ID != null ? !user2ID.equals(trade.user2ID) : trade.user2ID != null) return false;
        if (user1Offer != null ? !user1Offer.equals(trade.user1Offer) : trade.user1Offer != null)
            return false;
        if (user2Offer != null ? !user2Offer.equals(trade.user2Offer) : trade.user2Offer != null)
            return false;
        if (user1Accepted != null ? !user1Accepted.equals(trade.user1Accepted) : trade.user1Accepted != null)
            return false;
        if (user2Accepted != null ? !user2Accepted.equals(trade.user2Accepted) : trade.user2Accepted != null)
            return false;
        if (isComplete != null ? !isComplete.equals(trade.isComplete) : trade.isComplete != null)
            return false;
        return !(tradeID != null ? !tradeID.equals(trade.tradeID) : trade.tradeID != null);

    }

    @Override
    public int hashCode() {
        int result = user1name != null ? user1name.hashCode() : 0;
        result = 31 * result + (user2name != null ? user2name.hashCode() : 0);
        result = 31 * result + (user1ID != null ? user1ID.hashCode() : 0);
        result = 31 * result + (user2ID != null ? user2ID.hashCode() : 0);
        result = 31 * result + (user1Offer != null ? user1Offer.hashCode() : 0);
        result = 31 * result + (user2Offer != null ? user2Offer.hashCode() : 0);
        result = 31 * result + (user1Accepted != null ? user1Accepted.hashCode() : 0);
        result = 31 * result + (user2Accepted != null ? user2Accepted.hashCode() : 0);
        result = 31 * result + (isComplete != null ? isComplete.hashCode() : 0);
        result = 31 * result + (tradeID != null ? tradeID.hashCode() : 0);
        return result;
    }
}
