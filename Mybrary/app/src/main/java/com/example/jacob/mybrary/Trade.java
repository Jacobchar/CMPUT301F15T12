package com.example.jacob.mybrary;

/**
 * Created by davidross on 2015-11-02.
 * Completes trades by removing an item from one user1, and adding the same item to user2
 * This assumes that the trade has previously been accepted and will simply move the items.
 * Need to uncomment lines once removing books is implemented.
 */
public class Trade {

        public void tradeBooks(User userOne, Book userOneBook, User userTwo, Book userTwoBook){
            Inventory sourceInv = userOne.getInventory();
            Inventory destInv = userTwo.getInventory();

            sourceInv.addBook(userTwoBook);
           // sourceInv.removeBook(userOneBook);

            destInv.addBook(userOneBook);
           // destInv.removeBook(userTwoBook);
        }
}
