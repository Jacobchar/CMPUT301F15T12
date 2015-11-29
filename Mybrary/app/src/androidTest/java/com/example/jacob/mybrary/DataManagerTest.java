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
    public void testPutBook() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            if(!dataManager.storeBook(book)) {
                assertTrue(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
            }

            dataManager.removeBook(book.getItemID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetBook() {
        try {
            DataManager dataManager = DataManager.getInstance();
            Book book = new Book("testBook", 1, "testCategory", true);

            dataManager.storeBook(book);

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

            if (!dataManager.removeBook(book.getItemID().toString())) {
                assertFalse(FileManager.getInstance().fileExists("Books/" + book.getItemID().toString()));
            }
        } catch (Exception e) {
            fail();
        }
    }


    //=========================USERS===============================
    public void testPutUser() {
        try {
            DataManager dataManager = DataManager.getInstance();
            User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");

            if(!dataManager.storeUser(user)) {
                assertTrue(FileManager.getInstance().fileExists("Users/" + user.getUUID().toString()));
            }

            dataManager.removeBook(user.getUUID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetUser() {
        try {
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
        }
    }

    public void testSearchUsers() {
        try {
            DataManager dataManager = DataManager.getInstance();
            User user1 = new User("testName1", "testEmail1", "testPhNo1", "testGender1", "testBio1", "testCity1");
            User user2 = new User("testName2", "testEmail2", "testPhNo2", "testGender2", "testBio2", "testCity2");
            User user3 = new User("testName3", "testEmail3", "testPhNo3", "testGender3", "testBio3", "testCity3");

            dataManager.storeUser(user1);
            dataManager.storeUser(user2);
            dataManager.storeUser(user3);

            //Wait for entries to be indexed
            Thread.sleep(1000);

            ArrayList<User> returnedUsers = dataManager.searchUsers("{\"query\":{\"query_string\":{\"default_field\":\"name\",\"query\":\"testName2\"}}}");

            assertTrue(returnedUsers.size() == 1);
            assertTrue(returnedUsers.contains(user2));

            dataManager.removeBook(user1.getUUID().toString());
            dataManager.removeBook(user2.getUUID().toString());
            dataManager.removeBook(user3.getUUID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemoveUser() {
        try {
            DataManager dataManager = DataManager.getInstance();
            User user = new User("testName", "testEmail", "testPhNo", "testGender", "testBio", "testCity");

            dataManager.storeUser(user);

            if (dataManager.removeUser(user.getUUID().toString())) {
                assertFalse(FileManager.getInstance().fileExists("Users/" + user.getUUID().toString()));
            }
        } catch (Exception e) {
            fail();
        }
    }


    //=========================TRADES===============================
    public void testPutTrade() {

    }

    public void testGetTrade() {

    }

    public void testSearchTrades() {

    }

    public void testRemoveTrade() {

    }


    //=========================PHOTOS===============================
    public void testPutPhoto() {
        try {
            UUID photoID = new UUID(0xAAAAAAAA, 0x99999999);
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            if(!dataManager.storePhoto(photo)) {
                assertTrue(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));
            }

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testGetPhoto() {
        try {
            UUID photoID = new UUID(0xAAAAAAAA, 0x99999999);
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            dataManager.storePhoto(photo);

            Photo returnedPhoto = dataManager.retrievePhoto(photo.getPhotoID().toString());

            assertTrue(photo.equals(returnedPhoto));

            dataManager.removePhoto(photo.getPhotoID().toString());
        } catch (Exception e) {
            fail();
        }
    }

    public void testRemovePhoto() {
        try {
            UUID photoID = new UUID(0xAAAAAAAA, 0x99999999);
            DataManager dataManager = DataManager.getInstance();
            Photo photo = new Photo(100, "Bitmap", "This is so encoded", photoID);

            dataManager.storePhoto(photo);
            if(!dataManager.removePhoto(photo.getPhotoID().toString())) {
                assertFalse(FileManager.getInstance().fileExists("Photos/" + photo.getPhotoID().toString()));
            }
        } catch (Exception e) {
            fail();
        }
    }
}