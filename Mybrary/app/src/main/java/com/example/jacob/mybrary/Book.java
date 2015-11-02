package com.example.jacob.mybrary;

import java.util.ArrayList;

public class Book {

	private String name;
    private Integer quantity;
    private String category;
    private boolean sharedWithOthers;
    private Collection<String> comments;
    private UUID itemID;
    private Photo photo;
    private ArrayList observers;

    public void Book(String name, Integer quantity, String category, boolean sharedWithOthers){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.sharedWithOthers = sharedWithOthers;

        this.itemID = generateNewUUID();
    }

    UUID generateNewUUID(){
        UUID id = new UUID();
        return id;
    }

    void addNewComment(String comment){
        this.comments.add(comment);
    }

    void updateObservers(){

    }

}