package com.example.jacob.mybrary;

import android.widget.Button;

/**
 * Created by Victoria.
 *
 * Controller class for an item. Allows you to modify attributes and specs of a book you own.
 * Still under construction.
 *
 */

public class ItemController {

    private Button addPhoto;
    private Button addComment;
    private Button share;
    private Button changeCategory;

    void addPhoto(Photo photo, Book book){
        //book.photo = photo;
    }

    void addComment(String comment, Book book){
        book.getComments().add(comment);
    }

    void share(Book book){
        book.setSharedWithOthers(true);
    }

    void hide(Book book){
        book.setSharedWithOthers(false);
    }

    void changeCategory(String newCategory, Book book){
        book.setCategory(newCategory);
    }

}