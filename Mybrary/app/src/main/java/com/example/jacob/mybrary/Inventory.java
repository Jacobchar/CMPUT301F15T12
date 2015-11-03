package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/* Created By Victoria */

public class Inventory {

    private Collection<Book> inventoryList;
    private ArrayList observers;

    Book getBookByName(String name){

        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.name == name){
                return book;
            }
        }

        return null;
    }

    Collection<Book> getBooks(){
        return inventoryList;
    }

    Integer numCopies(Book inputBook){
        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.itemID == inputBook.itemID){
                return book.quantity;
            }
        }

        return 0;
    }

    Integer sizeInventory(){
        return inventoryList.size();
    }

    Boolean hasBook(Book inputBook){
        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.itemID == inputBook.itemID){
                return true;
            }
        }

        return false;
    }

    void updateObservers(){}


}
