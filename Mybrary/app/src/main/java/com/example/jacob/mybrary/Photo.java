package com.example.jacob.mybrary;

import android.graphics.Bitmap;

/*  Created By Victoria
This is to be done later, in project pt 5 */

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