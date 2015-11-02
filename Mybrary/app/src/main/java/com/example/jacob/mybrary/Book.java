package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Book {

    public String name;
    public Integer quantity;
    public String category;
    public boolean sharedWithOthers;
    public Collection<String> comments;
    public UUID itemID;
    public Photo photo;
    public ArrayList observers;

    public void Book(){
        this.itemID = generateNewUUID();
    }

    public void Book(String name, Integer quantity, String category, boolean sharedWithOthers){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.sharedWithOthers = sharedWithOthers;

        this.itemID = generateNewUUID();
    }

    UUID generateNewUUID(){
        UUID id = UUID.randomUUID();
        return id;
    }

    void addNewComment(String comment){
        this.comments.add(comment);
    }

    void updateObservers(){

    }

}