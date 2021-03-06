package com.example.jacob.mybrary;

import android.graphics.Bitmap;
import java.util.UUID;
/*
Copyright (C) 2015  Ben Schreiber , David Ross,Dominic Trottier,
                    Jake Charlebois, Mason Strong, Victoria Hessdorfer

        This file is part of Mybrary.

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 *
 * Basic photo class. Skeleton code(Not so skeleton anymore). Still needs to check valid format.
 * Maybe add an 'isFlagged' param to check whether phot should be dl'ed or not.
 *
 */

public class Photo {

    private Integer size;
    private String format;
    private String encodedImage;
    private UUID photoID;

    public Photo(Integer size, String format, String encodedImage, UUID photoID){
        this.size = size;
        this.format = format;
        this.encodedImage = encodedImage;
        this.photoID = photoID;
    }


    /**
     * Checks if a given photo is in the correct format.
     * @param format String containing photo's format
     * @return true/false if photo is in the valid format
     */
    public Boolean checkValidFormat(String format){
        return format == "JPG";
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
        return GsonManager.getInstance().toJson(image);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (size != null ? !size.equals(photo.size) : photo.size != null) return false;
        if (format != null ? !format.equals(photo.format) : photo.format != null) return false;
        if (encodedImage != null ? !encodedImage.equals(photo.encodedImage) : photo.encodedImage != null)
            return false;
        return !(photoID != null ? !photoID.equals(photo.photoID) : photo.photoID != null);

    }

    @Override
    public int hashCode() {
        int result = size != null ? size.hashCode() : 0;
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (encodedImage != null ? encodedImage.hashCode() : 0);
        result = 31 * result + (photoID != null ? photoID.hashCode() : 0);
        return result;
    }
}