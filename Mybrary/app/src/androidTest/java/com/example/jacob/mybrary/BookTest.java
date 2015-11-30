package com.example.jacob.mybrary;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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

    public void setUp(){
        LocalUser localUser = LocalUser.getInstance();
        Book book = new Book();
        localUser.getInventory().addBook(book);
    }

    public void testCreateBook() {
        Book book = new Book(name, quantity, category, sharedWithOthers);
    }

    /* Test Getters and Setters */

    public void testGetName(){
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        assertEquals(testBook.getName(), name);
    }

    public void testSetName(){
        Book testBook = new Book("", quantity, category, sharedWithOthers);
        testBook.setName(name);
        assertEquals(testBook.getName(), name);
    }

    public void testGetQuantity() {
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        assertEquals(testBook.getQuantity(), quantity);
    }

    public void testSetQuantity() {
        Book testBook = new Book(name, 0, category, sharedWithOthers);
        testBook.setQuantity(quantity);
        assertEquals(testBook.getQuantity(), quantity);
    }

    public void testGetCategory() {
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        assertEquals(testBook.getCategory(), category);
    }

    public void testSetCategory() {
        Book testBook = new Book(name, quantity, "", sharedWithOthers);
        testBook.setCategory(category);
        assertEquals(testBook.getCategory(), category);
    }

    public void testGetID(){
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        UUID testID = testBook.getItemID();
    }

    public void testGetSharedWithOthers(){
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        assertEquals(testBook.isSharedWithOthers(), sharedWithOthers);
    }

    public void testSetSharedWithOthers(){
        Book testBook = new Book(name, quantity, category, false);
        testBook.setSharedWithOthers(sharedWithOthers);
        assertEquals(testBook.isSharedWithOthers(), sharedWithOthers);
    }

    public void testAddNewComment(){
        String comment = "Test";
        Book testBook = new Book(name, quantity, category, sharedWithOthers);
        testBook.addNewComment(comment);

        Collection<String> t = testBook.getComments();
        Iterator i = t.iterator();

        Boolean found = false;
        while (i.hasNext()){
            String tmp = (String) i.next();
            if (tmp.equals(comment))
                found = true;
        }
        assertTrue(found);

    }

}

