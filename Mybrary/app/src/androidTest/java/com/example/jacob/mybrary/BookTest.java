package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Victoria on 2015-11-02.
 *
 * Tests for Book Class
 *
 */
public class BookTest extends ActivityInstrumentationTestCase2 {

    private String name = "WeirdoBook";
    private Integer quantity = 666;
    private String category = "Religion";
    private boolean sharedWithOthers = true;
    private Collection<String> comments;

    public BookTest() { super(Book.class); }

    public void testCreateBook() {
        Book book = new Book(name, quantity, category, sharedWithOthers);
    }

    public void testGetID(){
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        UUID testID = testBook.itemID;
    }


    /*

    public void testGetUUID(){
        User testUser = new User(name, phoneNum, gender, bio);
        UUID testID = testUser.getUUID();
    }

    public void testGetName(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotName = testUser.getName();
        assertEquals(gotName, name);
    }

    public void testGetPhoneNumber(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotPhoneNum = testUser.getPhoneNumber();
        assertEquals(gotPhoneNum, phoneNum);
    }

    public void testGetGender(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotGender = testUser.getGender();
        assertEquals(gotGender, gender);
    }

    public void testGetBio(){
        User testUser = new User(name, phoneNum, gender, bio);
        String gotBio = testUser.getBio();
        assertEquals(gotBio, bio);
    }

    */


}

