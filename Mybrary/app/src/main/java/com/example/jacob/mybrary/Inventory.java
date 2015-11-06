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

    /**
     * Grab a user's full inventory.
     * @return Returns an ArrayList of Books.
     */
    ArrayList<Book> getBooks(){
        return inventoryList;
    }

    /**
     * Add a book to a user's inventory.
     * @param book The book object you want to add to your inventory
     */
    public void addBook(Book book){

        inventoryList.add(book);

    }

    /**
     * Get the size of your inventory.
     * Note: number of unique books, not total copies of all books
     * @return returns inventory size
     */
    Integer sizeInventory(){
        return inventoryList.size();
    }

    /**
     * Returns book object, identified by it's name.
     * @param name takes the name of a book in your inventory
     * @return returns a Book object
     */
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

    /**
     * Deletes a book object, identified by it's name.
     * @param name takes the name of a book in your inventory
     * @return returns true/false depending on if your book was found & deleted
     */
    boolean deleteBookByName(String name){

        Book book = getBookByName(name);

        if (book == null) return false;

        inventoryList = getBooks();

        inventoryList.remove(book);

        return true;

    }

    /**
     * Returns the number of copies of a specified book.
     * @param inputBook Given book you want the quantity of
     * @return returns an integer of copies
     */
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

    /**
     * Checks if a book is in your inventory.
     * @param inputBook Given book you want to check
     * @return true/false if book is in your inventory
     */
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
