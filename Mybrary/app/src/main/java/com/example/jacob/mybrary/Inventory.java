package com.example.jacob.mybrary;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;

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


public class Inventory implements Serializable {

    private ArrayList<Book> inventoryList = new ArrayList<Book>();

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
    public void addBook(final Book book) {

        inventoryList.add(book);
        try {
            DataManager dataManager = DataManager.getInstance();
            dataManager.storeBook(book);
        } catch (IOException e){
            e.printStackTrace();
        }

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
            if (book.getName().equals(name)){
                return book;
            }
        }

        return null;
    }

    /**
     * Returns book object, identified by it's UUID.
     * @param id takes the id of a book in your inventory
     * @return returns a book object
     */
    Book getBookByID(UUID id){

        Book book;
        Iterator e = inventoryList.iterator();

        while (e.hasNext()){
            book = (Book) e.next();
            if (book.getItemID().compareTo(id) == 0){
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


        try {
            DataManager dataManager = DataManager.getInstance();
            dataManager.removeBook(book.getItemID().toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        return true;

    }


    /**
     * Deletes a book object, identified by it's id.
     * @param id takes the id of a book in your inventory
     * @return returns true/false depending on if your book was found & deleted
     */
    boolean deleteBookByID(UUID id){

        Book book = getBookByID(id);

        if (book == null) return false;

        inventoryList = getBooks();
        inventoryList.remove(book);


        try {
            DataManager dataManager = DataManager.getInstance();
            dataManager.removeBook(book.getItemID().toString());
        } catch (IOException e){
            e.printStackTrace();
        }

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

    public void convertFriendsArrayListToInventory(ArrayList<Book> arrayList){
        Iterator<Book> iterator = arrayList.iterator();
        while(iterator.hasNext()) {
            inventoryList.add(iterator.next());
        }
    }

}
