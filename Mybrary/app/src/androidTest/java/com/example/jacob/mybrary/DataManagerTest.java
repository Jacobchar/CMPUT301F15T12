package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dominic on 2015-11-06.
 *
 * Simple tests for DataManager.
 */
public class DataManagerTest extends AndroidTestCase {
    //=========================BOOKS===============================
    public void testPutBook() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            assertTrue(dataManager.storeBook(book));

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetBook() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            if (!dataManager.storeBook(book)) {
                fail();
            }

            Book returnedBook = dataManager.retrieveBook(book.getItemID().toString());

            assertTrue(book.equals(returnedBook));

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testSearchBooks() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book1 = new Book("testBook1", 1, "testCategory1", true);
            Book book2 = new Book("testBook2", 2, "testCategory2", true);
            Book book3 = new Book("testBook3", 3, "testCategory3", true);

            dataManager.storeBook(book1);
            dataManager.storeBook(book2);
            dataManager.storeBook(book3);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            ArrayList<Book> returnedBooks = dataManager.searchBooks("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\"testBook2\"}}}");

            assertTrue(returnedBooks.size() == 1);
            assertTrue(returnedBooks.contains(book2));

            dataManager.removeBook(book1.getItemID().toString());
            dataManager.removeBook(book2.getItemID().toString());
            dataManager.removeBook(book3.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemoveBook() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            dataManager.storeBook(book);

            assertTrue(dataManager.removeBook(book.getItemID().toString()));
        } catch (Exception e) {
            fail();
        }
    }


    //=========================USERS===============================
//    public void testPutUser() {
//        DataManager dataManager = DataManager.getInstance();
//        User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");
//
//        assertTrue(dataManager.storeUser(user));
//
//        dataManager.removeBook(user.getUUID().toString());
//    }
//
//    public void testGetUser() {
//        DataManager dataManager = DataManager.getInstance();
//        User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");
//
//        if (!dataManager.storeUser(user)) {
//            fail();
//        }
//
//        User returnedUser = dataManager.retrieveUser(user.getUUID().toString());
//
//        //TODO: Ensure that User has an equals() method.
//        assertTrue(user.equals(returnedUser));
//
//        dataManager.removeBook(user.getUUID().toString());
//    }
//
//    public void testSearchUsers() {
//        DataManager dataManager = DataManager.getInstance();
//        User user1 = new User("testName1", "testEmail1", "testPhNo1", "testGender1", "testBio1", "testCity1");
//        User user2 = new User("testName2", "testEmail2", "testPhNo2", "testGender2", "testBio2", "testCity2");
//        User user3 = new User("testName3", "testEmail3", "testPhNo3", "testGender3", "testBio3", "testCity3");
//
//        dataManager.storeUser(user1);
//        dataManager.storeUser(user2);
//        dataManager.storeUser(user3);
//
//        ArrayList<User> returnedUsers = dataManager.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\"testName2\"}}}");
//
//        assertTrue(returnedUsers.size() == 1);
//        assertTrue(returnedUsers.contains(user2));
//
//        dataManager.removeBook(user1.getUUID().toString());
//        dataManager.removeBook(user2.getUUID().toString());
//        dataManager.removeBook(user3.getUUID().toString());
//    }
//
//    public void testRemoveUser() {
//        DataManager dataManager = DataManager.getInstance();
//        User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");
//
//        dataManager.storeUser(user);
//
//        assertTrue(dataManager.removeUser(user.getUUID().toString()));
//    }


    //=========================TRADES===============================
//    public void testPutTrade() {
//        DataManager dataManager = DataManager.getInstance();
//        Trade trade = new Trade();
//
//        assertTrue(dataManager.storeTrade(trade));
//
//        dataManager.removeTrade(trade.tradeID.toString());
//    }
//
//    public void testGetTrade() {
//        DataManager dataManager = DataManager.getInstance();
//        Trade trade = new Trade();
//
//        if (!dataManager.storeTrade(trade)) {
//            fail();
//        }
//
//        Trade returnedTrade = dataManager.retrieveTrade(trade.tradeID.toString());
//
//        //TODO: Ensure that Trade has an equals() method.
//        assertTrue(trade.equals(returnedTrade));
//
//        dataManager.removeTrade(trade.tradeID.toString());
//    }
//
//    public void testSearchTrades() {
//        DataManager dataManager = DataManager.getInstance();
//        Trade trade1 = new Trade();
//        Trade trade2 = new Trade();
//        Trade trade3 = new Trade();
//
//        dataManager.storeTrade(trade1);
//        dataManager.storeTrade(trade2);
//        dataManager.storeTrade(trade3);
//
//        ArrayList<Trade> returnedTrades = dataManager.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"val\",\"query\":2}}}");
//
//        assertTrue(returnedTrades.size() == 1);
//        assertTrue(returnedTrades.contains(trade2));
//
//        dataManager.removeBook(trade1.tradeID.toString());
//        dataManager.removeBook(trade2.tradeID.toString());
//        dataManager.removeBook(trade3.tradeID.toString());
//    }
//
//    public void testRemoveTrade() {
//        DataManager dataManager = DataManager.getInstance();
//        Trade trade = new Trade();
//
//        dataManager.storeTrade(trade);
//
//        assertTrue(dataManager.removeTrade(trade.tradeID.toString()));
//    }


    //=========================PHOTOS===============================
//    public void testPutPhoto() {
//        DataManager dataManager = DataManager.getInstance();
//        Photo photo = new Photo();
//
//        assertTrue(dataManager.storePhoto(photo));
//
//        dataManager.removeBook(photo.getUUID().toString());
//    }
//
//    public void testGetPhoto() {
//        DataManager dataManager = DataManager.getInstance();
//        Photo photo = new Photo();
//
//        if (!dataManager.storePhoto(photo)) {
//            fail();
//        }
//
//        Photo returnedPhoto = dataManager.retrievePhoto(photo.getUUID().toString());
//
//        //TODO: Ensure that Photo has an equals() method.
//        assertTrue(photo.equals(returnedPhoto));
//
//        dataManager.removePhoto(photo.getUUID().toString());
//    }
//
//    public void testSearchPhotos() {
//        DataManager dataManager = DataManager.getInstance();
//        Photo photo1 = new Photo();
//        Photo photo2 = new Photo();
//        Photo photo3 = new Photo();
//
//        dataManager.storePhoto(photo1);
//        dataManager.storePhoto(photo2);
//        dataManager.storePhoto(photo3);
//
//        ArrayList<Photo> returnedPhotos = dataManager.searchPhotos("{\"query\":{\"query_string\":{\"default_field\":\"val\",\"query\":2}}}");
//
//                assertTrue(returnedPhotos.size() == 1);
//        assertTrue(returnedPhotos.contains(photo2));
//
//        dataManager.removePhoto(photo1.getUUID().toString());
//        dataManager.removePhoto(photo2.getUUID().toString());
//        dataManager.removePhoto(photo3.getUUID().toString());
//    }
//
//    public void testRemovePhoto() {
//        DataManager dataManager = DataManager.getInstance();
//        Photo photo = new Photo();
//
//        dataManager.storePhoto(photo);
//
//        assertTrue(dataManager.removePhoto(photo.getUUID().toString()));
//    }
}
