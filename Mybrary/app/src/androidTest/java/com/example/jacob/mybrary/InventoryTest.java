package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by victoria on 2015-11-05.
 *
 * Adapted from the test cases that David & Jake wrote.
 */
public class InventoryTest extends ActivityInstrumentationTestCase2{

    public InventoryTest() {
        super(Inventory.class);
    }

    // Use Case 1.2
    public void testAddBook() {
        Inventory bookList = new Inventory();
        Book book = new Book("Expensive Textbook", 12, "Academia", true);
        bookList.addBook(book);
        assertTrue(bookList.hasBook(book));
    }
    // Use Case 1.4
    public void testDeleteBook() {
        Inventory bookList = new Inventory();
        Book book = new Book("Expensive Textbook", 12, "Academia", true);
        bookList.addBook(book);
        bookList.deleteBookByName(book.getName());
        assertFalse(bookList.hasBook(book));
    }

    public void testHasBook() {
        Inventory bookList = new Inventory();
        Book book = new Book("Expensive Textbook", 12, "Academia", true);
        assertFalse(bookList.hasBook(book));
        bookList.addBook(book);
        assertTrue(bookList.hasBook(book));
    }

    public void testSizeInventory() {
        Inventory bookList = new Inventory();
        Book book1 = new Book("Expensive Textbook", 12, "Academia", true);
        bookList.addBook(book1);
        Book book2 = new Book("Expensive Textbook Number two", 1, "Academia", true);
        bookList.addBook(book2);
        int books = bookList.sizeInventory();
        assertEquals(2, books);
    }

    public void testNumCopies() {
        Inventory bookList = new Inventory();
        Book book2 = new Book("Expensive Textbook", 12, "Academia", true);
        bookList.addBook(book2);
        int numCopies = bookList.numCopies(book2);
        assertEquals(12, numCopies);
    }


    public void testConvertFriendsArrayListToInventory(){
        ArrayList<Book> bookArray = new ArrayList<>();
        Book book = new Book("Expensive Textbook", 12, "Academia", true);
        Book book2 = new Book("Expensive Textbook 2", 2, "Academia", true);
        bookArray.add(book);
        bookArray.add(book2);

        Inventory inv = new Inventory();
        inv.convertFriendsArrayListToInventory(bookArray);

        assertTrue(inv.hasBook(book));
        assertTrue(inv.hasBook(book2));
    }

    public void testGetPublicBooks(){
        Inventory inv = new Inventory();
        Book book = new Book("Expensive Textbook", 12, "Academia", true);
        Book book2 = new Book("Expensive Textbook 2", 2, "Academia", false);

        inv.addBook(book);
        inv.addBook(book2);

        ArrayList<Book> array = inv.getPublicBooks();

        assertTrue(array.contains(book));
        assertFalse(array.contains(book2));


    }

}