package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;

public class Inventory {

    private Collection<Book> inventoryList;
    private ArrayList observers;

    Book getBookByName(String name){
        Book book = new Book();
        return book;
    }

    Collection<Book> getBooks(){
        return inventoryList;
    }

    Integer numCopies(Book book){
        return book.quantity;
    }

    Integer sizeInventory(){
        return 1;
    }

    Boolean hasBook(Book book){
        return true;
    }

    void updateObservers(){}


}
