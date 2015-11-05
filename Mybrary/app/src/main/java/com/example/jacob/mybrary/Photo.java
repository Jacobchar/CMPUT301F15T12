package com.example.jacob.mybrary;

import android.graphics.Bitmap;

/**
 * Created by Victoria.
 *
 * Basic photo class. To be completed in project P5.
 *
 */

public class Photo {

    private Integer size;
    private String format;
    private String encodedImage;


    Boolean checkValidFormat(String format){
        return true;
    }

    Boolean checkValidSize(Integer size){
       if (size < 65536){
           return true;
       } else {
           return false;
       }

    }

    String convertImageToJson(Bitmap image){
        return "";
    }


}