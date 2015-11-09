package com.example.jacob.mybrary;

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

    public ArrayList<Book> searchBooks(String query) {
        return null;
    }

    //======================= USERS ==============================
    public Boolean storeUser(User user) {
        return false;
    }

    public Boolean removeUser(String id) {
        return false;
    }

    public User retrieveUser(String id) {
        return null;
    }

    public ArrayList<User> searchUsers(String query) {
        return null;
    }

    //======================= TRADES ==============================
    public Boolean storeTrade(Trade trade) {
        return false;
    }

    public Boolean removeTrade(String id) {
        return false;
    }

    public Trade retrieveTrade(String id) {
        return null;
    }

    public ArrayList<Trade> searchTrades(String query) {
        return null;
    }

    //======================= PHOTOS ==============================
    public Boolean storePhoto(Photo photo) {
        return false;
    }

    public Boolean removePhoto(String id) {
        return false;
    }

    public Photo retrievePhoto(String id) {
        return null;
    }

    public ArrayList<Photo> searchPhotos(String query) {
        return null;
    }

}
