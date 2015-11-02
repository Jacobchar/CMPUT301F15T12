package com.example.jacob.mybrary;

public class Inventory{

    private Collection<Books> inventoryList;
    private ArrayList observers;

    Book getBookByName(String name){
    }

    Collection<Book> getBooks(){
    }

    Integer numCopies(Book book){
        return book.quantity;
    }

    Integer sizeInventory(){
    }

    Boolean hasBook(Book book){
    }

    void updateObservers(){}

}
