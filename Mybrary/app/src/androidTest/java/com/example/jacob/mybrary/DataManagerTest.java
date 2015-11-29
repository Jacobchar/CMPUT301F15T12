package com.example.jacob.mybrary;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dominic on 2015-11-06.
 *
 * Simple tests for DataManager.
 */
public class DataManagerTest extends AndroidTestCase {
    //=========================BOOKS===============================
    public void testPutBookOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            if(!dataManager.storeBook(book)) {
                fail();
                //assertTrue(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
            }

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testGetBookOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
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
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testSearchBooksOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Book book1 = new Book("testBook1", 1, "testCategory1", true);
            Book book2 = new Book("testBook2", 2, "testCategory2", true);
            Book book3 = new Book("testBook3", 3, "testCategory3", true);

            dataManager.storeBook(book1);
            dataManager.storeBook(book2);
            dataManager.storeBook(book3);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            ArrayList<Book> returnedBooks = dataManager.searchBooks("{\"query\":{\"query_string\":{\"default_field\":\"itemID\",\"query\":\"" + book2.getItemID().toString() + "\"}}}");

            assertTrue(returnedBooks.size() == 1);
            assertTrue(returnedBooks.contains(book2));

            dataManager.removeBook(book1.getItemID().toString());
            dataManager.removeBook(book2.getItemID().toString());
            dataManager.removeBook(book3.getItemID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testRemoveBookOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            if (!dataManager.storeBook(book)) {
                fail();
            }

            if (!dataManager.removeBook(book.getItemID().toString())) {
                assertFalse(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
            }
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testPutBookOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            assertFalse(dataManager.storeBook(book));
            assertTrue(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
            assertTrue(FileManager.getInstance().fileExists("Offline/Books/" + book.getItemID().toString()));

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetBookOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            assertFalse(dataManager.storeBook(book));

            Book returnedBook = dataManager.retrieveBook(book.getItemID().toString());

            assertTrue(book.equals(returnedBook));

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemoveBookOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            assertFalse(dataManager.storeBook(book));

            assertFalse(dataManager.removeBook(book.getItemID().toString()));
            assertFalse(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
        } catch (Exception e) {
            fail();
        }
    }

    //=========================USERS===============================
    public void testPutUserOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");

            if(!dataManager.storeUser(user)) {
                assertTrue(FileManager.getInstance().fileExists("Users/" + user.getUUID().toString()));
            }

            dataManager.removeBook(user.getUUID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testGetUserOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");

            dataManager.storeUser(user);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            User returnedUser = dataManager.retrieveUser(user.getUUID().toString());

            assertTrue(user.equals(returnedUser));

            dataManager.removeBook(user.getUUID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testSearchUsersOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            User user1 = new User("testName1", "testEmail1", "testPhNo1", "testGender1", "testBio1", "testCity1");
            User user2 = new User("testName2", "testEmail2", "testPhNo2", "testGender2", "testBio2", "testCity2");
            User user3 = new User("testName3", "testEmail3", "testPhNo3", "testGender3", "testBio3", "testCity3");

            dataManager.storeUser(user1);
            dataManager.storeUser(user2);
            dataManager.storeUser(user3);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            ArrayList<User> returnedUsers = dataManager.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"myUUID\",\"query\":\"" + user2.getUUID().toString() + "\"}}}");

            assertTrue(returnedUsers.size() == 1);
            assertTrue(returnedUsers.contains(user2));

            dataManager.removeBook(user1.getUUID().toString());
            dataManager.removeBook(user2.getUUID().toString());
            dataManager.removeBook(user3.getUUID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testRemoveUserOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");

            dataManager.storeUser(user);

            if (dataManager.removeUser(user.getUUID().toString())) {
                assertFalse(FileManager.getInstance().fileExists("Users/" + user.getUUID().toString()));
            }
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }


    //=========================TRADES===============================
    public void testPutTradeOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            if(!dataManager.storeTrade(trade)) {
                fail();
                //assertTrue(FileManager.getInstance().fileExists("Trades/" + trade.getTradeID().toString()));
            }

            dataManager.removeBook(trade.getTradeID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testGetTradeOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            if (!dataManager.storeTrade(trade)) {
                fail();
            }

            Trade returnedTrade = dataManager.retrieveTrade(trade.getTradeID().toString());

            assertTrue(trade.equals(returnedTrade));

            dataManager.removeBook(trade.getTradeID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testSearchTradesOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade1 = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));
            Trade trade2 = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));
            Trade trade3 = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            dataManager.storeTrade(trade1);
            dataManager.storeTrade(trade2);
            dataManager.storeTrade(trade3);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            ArrayList<Trade> returnedTrades = dataManager.searchTrades("{\"query\":{\"query_string\":{\"default_field\":\"tradeID\",\"query\":\"" + trade2.getTradeID().toString() + "\"}}}");

            assertTrue(returnedTrades.size() == 1);
            assertTrue(returnedTrades.contains(trade2));

            dataManager.removeBook(trade1.getTradeID().toString());
            dataManager.removeBook(trade2.getTradeID().toString());
            dataManager.removeBook(trade3.getTradeID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testRemoveTradeOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            if (!dataManager.storeTrade(trade)) {
                fail();
            }

            assertTrue(dataManager.removeTrade(trade.getTradeID().toString()));
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testPutTradeOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            assertFalse(dataManager.storeTrade(trade));
            assertTrue(FileManager.getInstance().fileExists("Trades/" + trade.getTradeID().toString()));
            assertTrue(FileManager.getInstance().fileExists("Offline/Trades/" + trade.getTradeID().toString()));

            dataManager.removeTrade(trade.getTradeID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetTradeOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            assertFalse(dataManager.storeTrade(trade));

            Trade returnedTrade = dataManager.retrieveTrade(trade.getTradeID().toString());

            assertTrue(trade.equals(returnedTrade));

            dataManager.removeBook(trade.getTradeID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemoveTradeOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            DataManager dataManager = DataManager.getInstance();
            Trade trade = new Trade(new User("", "", "", "", "", ""), new User("", "", "", "", "", ""));

            assertFalse(dataManager.storeTrade(trade));

            assertFalse(dataManager.removeTrade(trade.getTradeID().toString()));
            assertFalse(FileManager.getInstance().fileExists("Trades/" + trade.getTradeID().toString()));
        } catch (Exception e) {
            fail();
        }
    }


    //=========================PHOTOS===============================
    public void testPutPhotoOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            assertTrue(dataManager.storePhoto(photo));
            //assertTrue(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testGetPhotoOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            if (!dataManager.storePhoto(photo)) {
                fail();
            }

            Photo returnedPhoto = dataManager.retrieveOnlinePhoto(photo.getPhotoID().toString());

            assertTrue(photo.equals(returnedPhoto));

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testRemovePhotoOnline() {
        try {
            ConnectionManager.getInstance().setDebugOnline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            dataManager.storePhoto(photo);
            assertTrue(dataManager.removePhoto(photo.getPhotoID().toString()));
            //assertFalse(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));
        } catch (Exception e) {
            fail();
        } finally {
            ConnectionManager.getInstance().setDebugOffline();
        }
    }

    public void testPutPhotoOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            assertFalse(dataManager.storePhoto(photo));
            assertTrue(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));
            assertTrue(FileManager.getInstance().fileExists("Offline/Photos/" + photo.getPhotoID().toString()));

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetPhotoOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            assertFalse(dataManager.storePhoto(photo));

            Photo returnedPhoto = dataManager.retrieveCachedPhoto(photo.getPhotoID().toString());

            assertTrue(photo.equals(returnedPhoto));

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemovePhotoOffline() {
        try {
            ConnectionManager.getInstance().setDebugOffline();
            UUID photoID = UUID.randomUUID();
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            assertFalse(dataManager.storePhoto(photo));

            assertFalse(dataManager.removePhoto(photo.getPhotoID().toString()));
            assertFalse(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));
        } catch (Exception e) {
            fail();
        }
    }
}