package com.example.jacob.mybrary;

import android.graphics.Bitmap;

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

    /**
     * Checks if a given photo is in the correct format.
     * @param format String containing photo's format
     * @return true/false if photo is in the valid format
     */
    Boolean checkValidFormat(String format){
        return true;
    }


    /**
     * Checks if a given photo is a valid size.
     * @param size Photo's size in an integer
     * @return true/false if photo is a valid size
     */
    Boolean checkValidSize(Integer size){
       if (size < 65536){
           return true;
       } else {
           return false;
       }

    }

    /**
     * Converts image to a Json object.
     * @param image Bitmap of given image.
     * @return returns a json string.
     */
    String convertImageToJson(Bitmap image){
        return "";
    }


}