package com.example.jacob.mybrary;

import android.widget.Button;
import java.util.ArrayList;
import java.util.Collection;

public class ItemController {

    private Book thisBook = new Book();
    private Button addPhoto;
    private Button addComment;
    private Button share;
    private Button changeCategory;

    void addPhoto(Photo photo){
        thisBook.photo = photo;
    }

    void addComment(String comment){
        thisBook.comments.add(comment);
    }

    void share(){
        thisBook.sharedWithOthers = true;
    }

    void dontShare(){
        thisBook.sharedWithOthers = false;
    }

    void changeCategory(String newCategory){
        thisBook.category = newCategory;
    }
}