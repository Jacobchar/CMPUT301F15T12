package com.example.jacob.mybrary;

import java.util.List;

/**
 * Created by davidross on 2015-11-02.
 * Completes trades by removing an item from one user1, and adding the same item to user2
 * This assumes that the trade has previously been accepted and will simply move the items.
 * Need to uncomment lines once removing books is implemented.
 */
public class Trade {
    Inventory user1Inventory;
    Inventory user2Inventory;

    public Trade(User user1, User user2){
        user1Inventory = user1.getInventory();
        user2Inventory = user2.getInventory();
    }

    public void tradeBooks(List<Book> user1TradeOffer, List<Book> user2TradeOffer) {
        addRecievedBooks(user2TradeOffer, user1Inventory);
        addRecievedBooks(user1TradeOffer, user2Inventory);

        removeTradedBooks(user1TradeOffer, user1Inventory);
        removeTradedBooks(user2TradeOffer, user2Inventory);
    }

    public void addRecievedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
            inventory.addBook(book);
        }
    }

    public void removeTradedBooks(List<Book> bookList, Inventory inventory){
        for(Book book: bookList){
            // Uncomment once books can be removed!
           // inventory.removeBook(book);
        }
    }


}
