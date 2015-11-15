package com.example.jacob.mybrary;

import java.util.List;
import java.util.UUID;

/**
 * Completes trades by removing an item from one user1, and adding the same item to user2
 * provided both users have accepted the trade
 *  Created by davidross on 2015-11-02.
 */

public class Trade {
    private UUID user1;
    private UUID user2;
    private Inventory user1Inventory;
    private Inventory user2Inventory;
    private Boolean user1Accepted = false;
    private Boolean user2Accepted = false;
    private UUID tradeID;

    /**
     * Trade between two users
     * @param user1 One user who is taking part of the trade
     * @param user2 A second user who is taking part in the trade
     */
    public Trade(User user1, User user2){
        user1Inventory = user1.getInventory();
        user2Inventory = user2.getInventory();
        tradeID = tradeID.randomUUID();
        this.user1 = user1.getUUID();
        this.user2 = user2.getUUID();
    }

    /**
     * Perform the trade between users, using a list of books that they both want to trade
     * @param user1TradeOffer List of books that user 1 wishes to trade
     * @param user2TradeOffer List of books that user 2 wishes to trade
     */
    public void tradeBooks(List<Book> user1TradeOffer, List<Book> user2TradeOffer) {
        if(getUser1Accepted()  && getUser2Accepted()) {
            addReceivedBooks(user2TradeOffer, user1Inventory);
            addReceivedBooks(user1TradeOffer, user2Inventory);

            removeTradedBooks(user1TradeOffer, user1Inventory);
            removeTradedBooks(user2TradeOffer, user2Inventory);
        }
    }

    /**
     * Add new books to the users inventory
     * @param bookList List of books that the user will be receiving
     * @param inventory Inventory of the user receiving the books
     */
    private void addReceivedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
            inventory.addBook(book);
        }
    }

    /**
     * Remove old books from the users inventory
     * @param bookList List of books that will be removed from the user
     * @param inventory Inventory of the user having books removed
     */
    private void removeTradedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
          inventory.deleteBookByName(book.getName());
        }
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
        return "Trade completed";
    }

    /**
     * Get the unique identifier for user 1
     * @return UUID for user 1
     */
    public UUID getUser1UUID(){
        return this.user1;
    }

    /**
     * Get the unique identifier for user 2
     * @return UUID for user 2
     */
    public UUID getUser2UUID(){
        return this.user2;
    }

    /**
     * Get the unique identifier for a trade
     * @return UUID for the trade
     */
    public UUID getTradeID(){
        return this.tradeID;
    }

}
