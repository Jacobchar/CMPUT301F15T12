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
    private UUID ownerID;

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
        this.ownerID = LocalUser.getInstance().getUUID();
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
        this.ownerID = LocalUser.getInstance().getUUID();
    }

    /**
     * Creates string to be displayed to the user to describe a book.
     * @return Returns string to be displayed in Inventory Activity
     */
    @Override
    public String toString(){
        String string = "Name: " + name + ", Quantity: " + quantity + ", Category: " + category;

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

    public UUID getOwnerID() { return ownerID; }

    public void setOwnerID(UUID newID) {
        this.ownerID = newID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (sharedWithOthers != book.sharedWithOthers) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (quantity != null ? !quantity.equals(book.quantity) : book.quantity != null)
            return false;
        if (category != null ? !category.equals(book.category) : book.category != null)
            return false;
        if (comments != null ? !comments.equals(book.comments) : book.comments != null)
            return false;
        if (itemID != null ? !itemID.equals(book.itemID) : book.itemID != null) return false;
        return !(photoIDs != null ? !photoIDs.equals(book.photoIDs) : book.photoIDs != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (sharedWithOthers ? 1 : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (itemID != null ? itemID.hashCode() : 0);
        result = 31 * result + (photoIDs != null ? photoIDs.hashCode() : 0);
        return result;
    }
}