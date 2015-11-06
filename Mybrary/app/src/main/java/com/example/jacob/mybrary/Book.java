package com.example.jacob.mybrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Victoria.
 *
 * Model class that represents the book class. Can create new books, add comments, and get and set
 * book attributes. The toString() method dictates what will be displayed in the inventory activity.
 *
 */

public class Book {

    private String name;
    private Integer quantity;
    private String category;
    private boolean sharedWithOthers;
    private ArrayList<String> comments = new ArrayList<String>();
    private UUID itemID;

    public ArrayList<UUID> photoIDs;
    public ArrayList observers;

    /**
     * Basic empty Book constructor.
     */
    public Book(){
        this.name = "N/A";
        this.quantity = 0;
        this.category = "N/A";
        this.sharedWithOthers = false;

        this.itemID = UUID.randomUUID();
    }

    /**
     * Basic Book constructor, takes inputs.
     * @param name Name of the book
     * @param quantity Quantity of this book in your inventory
     * @param category Category this book falls under
     * @param sharedWithOthers Boolean that demonstrates if this item is private or shared
     */
    public Book(String name, Integer quantity, String category, boolean sharedWithOthers){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.sharedWithOthers = sharedWithOthers;

        this.itemID = UUID.randomUUID();
    }

    /**
     * Creates string to be displayed to the user to describe a book.
     * @return Returns string to be displayed in Inventory Activity
     */
    @Override
    public String toString(){
        String string = "";

        string = "Name: " + name + ", Quantity: " + quantity + ", Category: " + category;

        // sharedWithOthers
        if (sharedWithOthers) {
            string = string + " , is shared with others ";
        } else {
            string = string + ", isn't shared with others ";
        }

        // loop through comments
        if (comments.size() == 0) {
            string = string + ", No comments.";
        } else {
            string = string + ", Comments: ";
            Iterator<String> i = comments.iterator();
            while (i.hasNext()){
                string = string + i.next();
            }
        }

        return string;
    }

    /**
     * Adds a comment to the comment arrayList of a book.
     * @param comment Comment user wants to add to the book
     */
    public void addNewComment(String comment){
        this.comments.add(comment);
    }

    private void updateObservers(){
        // to be implemented
    }

    /**
     * Compares two books to eachother via itemID.
     * @param book Book you'd like to compare your current book to
     * @return 0, 1, -1: similar to basic compareTo function
     */
    public int compareTo(Object book) {

        return itemID.compareTo(((Book) book).itemID);

    }

    /* Endless Getters and Setters */

    public Collection<String> getComments() { return comments; }

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

    public UUID getItemID() { return itemID; }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}