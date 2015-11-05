package com.example.jacob.mybrary;

import java.util.List;
import java.util.UUID;

/**
 * Created by davidross on 2015-11-02.
 * Completes trades by removing an item from one user1, and adding the same item to user2
 * provided both users have accepted the trade
 */

public class Trade {
    UUID user1;
    UUID user2;
    Inventory user1Inventory;
    Inventory user2Inventory;
    Boolean user1Accepted = false;
    Boolean user2Accepted = false;
    UUID tradeID;

    public Trade(User user1, User user2){
        user1Inventory = user1.getInventory();
        user2Inventory = user2.getInventory();
        tradeID = tradeID.randomUUID();
        this.user1 = user1.getUUID();
        this.user2 = user2.getUUID();
    }

    public void tradeBooks(List<Book> user1TradeOffer, List<Book> user2TradeOffer) {
        if(getUser1Accepted()  && getUser2Accepted()) {
            addReceivedBooks(user2TradeOffer, user1Inventory);
            addReceivedBooks(user1TradeOffer, user2Inventory);

            removeTradedBooks(user1TradeOffer, user1Inventory);
            removeTradedBooks(user2TradeOffer, user2Inventory);
        }
    }

    public void addReceivedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
            inventory.addBook(book);
        }
    }

    public void removeTradedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
          inventory.deleteBookByName(book.getName());
        }
    }

    public void setUser1Accepted(Boolean accepted){
        user1Accepted = accepted;
    }

    public void setUser2Accepted(Boolean accepted){
        user2Accepted = accepted;
    }

    public Boolean getUser1Accepted(){
        return this.user1Accepted;
    }

    public Boolean getUser2Accepted(){
        return this.user2Accepted;
    }
}
