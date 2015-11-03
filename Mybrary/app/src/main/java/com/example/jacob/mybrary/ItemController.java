package com.example.jacob.mybrary;

import android.widget.Button;

/* Created By Victoria */

public class ItemController {

    private Button addPhoto;
    private Button addComment;
    private Button share;
    private Button changeCategory;

    void addPhoto(Photo photo, Book book){
        book.photo = photo;
    }

    void addComment(String comment, Book book){
        book.comments.add(comment);
    }

    void share(Book book){
        book.sharedWithOthers = true;
    }

    void hide(Book book){
        book.sharedWithOthers = false;
    }

    void changeCategory(String newCategory, Book book){
        book.category = newCategory;
    }
}