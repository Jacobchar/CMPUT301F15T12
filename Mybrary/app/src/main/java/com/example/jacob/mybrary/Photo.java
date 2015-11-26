package com.example.jacob.mybrary;

import android.graphics.Bitmap;

import java.util.UUID;

/**
 * Created by Victoria.
 *
 * Basic photo class. Skeleton code. To be completed in project P5.
 *
 */

public class Photo {

    private Integer size;
    private String format;
    private String encodedImage;
    private UUID photoID;

    /**
     * Checks if a given photo is in the correct format.
     * @param format String containing photo's format
     * @return true/false if photo is in the valid format
     */
    public Boolean checkValidFormat(String format){
        return true;
    }


    /**
     * Checks if a given photo is a valid size.
     * @param size Photo's size in an integer
     * @return true/false if photo is a valid size
     */
    public Boolean checkValidSize(Integer size){
        return size < 65536;
    }

    /**
     * Converts image to a Json object.
     * @param image Bitmap of given image.
     * @return returns a json string.
     */
    public String convertImageToJson(Bitmap image){
        GsonManager gman = new GsonManager();
        return gman.toJson(image);
    }

    /*All the Getters and Setters*/
    public Integer getSize() {return size;}
    public void setSize(Integer size) {this.size = size;}
    public String getFormat() {return format;}
    public void setFormat(String format) {this.format = format;}
    public String getEncodedImage() {return encodedImage;}
    public void setEncodedImage(String encodedImage) {this.encodedImage = encodedImage;}
    public UUID getPhotoID() {return photoID;}
    public void setPhotoID(UUID photoID) {this.photoID = photoID;}

}