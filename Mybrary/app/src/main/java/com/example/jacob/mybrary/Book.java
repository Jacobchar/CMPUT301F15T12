package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/* Created By Victoria */

public class Book {

    private String name;
    private Integer quantity;
    private String category;
    private boolean sharedWithOthers;
    private Collection<String> comments;
    private UUID itemID;

    public Photo photo;
    public ArrayList observers;

    public Book(){
        this.name = "N/A";
        this.quantity = 0;
        this.category = "N/A";
        this.sharedWithOthers = false;

        this.itemID = generateNewUUID();
    }


    public Book(String name, Integer quantity, String category, boolean sharedWithOthers){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.sharedWithOthers = sharedWithOthers;

        this.itemID = generateNewUUID();
    }

    public UUID generateNewUUID(){
        UUID id = UUID.randomUUID();
        return id;
    }

    public void addNewComment(String comment){
        this.comments.add(comment);
    }

    public Collection<String> getComments() {
        return comments;
    }

    private void updateObservers(){
        // to be implemented
    }

    public int compareTo(Object book) {

        return itemID.compareTo(((Book) book).itemID);

    }

    /* Endless Getters and Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isSharedWithOthers() {
        return sharedWithOthers;
    }

    public void setSharedWithOthers(boolean sharedWithOthers) {
        this.sharedWithOthers = sharedWithOthers;
    }

    public UUID getItemID() {
        return itemID;
    }

    public void setItemID(UUID itemID) {
        this.itemID = itemID;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}