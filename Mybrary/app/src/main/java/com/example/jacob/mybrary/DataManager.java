package com.example.jacob.mybrary;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Dominic on 2015-11-04.
 *
 * This data management abstraction offers a simplified way of retrieving data. The data may either
 * be remote or cached.
 */
public class DataManager {

    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() { return ourInstance; }

    private DataManager() {

    }

    //======================= BOOKS ==============================
    public Boolean storeBook(Book book) {
        return false;
    }

    public Boolean removeBook(String id) {
        return false;
    }

    public Book retrieveBook(String id) {
        return null;
    }

    public ArrayList<Book> searchBooks(String path, String query) {
        return null;
    }

    //======================= USERS ==============================
    public Boolean storeUser(User user) {
        return false;
    }

    public Boolean removeUser(String id) {
        return false;
    }

    public Book retrieveUser(String id) {
        return null;
    }

    public ArrayList<User> searchUsers(String path, String query) {
        return null;
    }

    //======================= TRADES ==============================
    public Boolean storeTrade(Trade trade) {
        return false;
    }

    public Boolean removeTrade(String id) {
        return false;
    }

    public Book retrieveTrade(String id) {
        return null;
    }

    public ArrayList<Trade> searchTrades(String path, String query) {
        return null;
    }

    //======================= PHOTOS ==============================
    public Boolean storePhoto(Photo photo) {
        return false;
    }

    public Boolean removePhoto(String id) {
        return false;
    }

    public Book retrievePhoto(String id) {
        return null;
    }

    public ArrayList<Photo> searchPhotos(String path, String query) {
        return null;
    }

}
