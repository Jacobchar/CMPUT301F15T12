package com.example.jacob.mybrary;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Victoria.
 *
 * Model class that represents a user's inventory. Allows for basic inventory functionality such as
 * getting the inventory, deleting a book, searching for a book by name, deleting a book, checking
 * if it has a book, and checking the size of the Inventory.
 *
 * Still under construction.
 *
 */

public class Inventory {

    private ArrayList<Book> inventoryList = new ArrayList<Book>();
    private ArrayList observers;

    ArrayList<Book> getBooks(){
        return inventoryList;
    }

    public void addBook(Book book){

        inventoryList.add(book);

    }

    Integer sizeInventory(){
        return inventoryList.size();
    }

    Book getBookByName(String name){

        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.getName() == name){
                return book;
            }
        }

        return null;
    }

    boolean deleteBookByName(String name){

        Book book = getBookByName(name);

        if (book == null) return false;

        inventoryList = getBooks();

        inventoryList.remove(book);

        return true;

    }

    Integer numCopies(Book inputBook){
        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.getItemID() == inputBook.getItemID()){
                return book.getQuantity();
            }
        }

        return 0;
    }

    Boolean hasBook(Book inputBook){
        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.getItemID() == inputBook.getItemID()){
                return true;
            }
        }

        return false;
    }

    void updateObservers(){}


}
