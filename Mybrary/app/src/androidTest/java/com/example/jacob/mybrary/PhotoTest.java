package com.example.jacob.mybrary;

import android.test.ActivityInstrumentationTestCase2;

import java.util.UUID;

/**
 * Created by Jacob on 2015-11-25.
 *
 * Tests for the Photo Class. Still need to fix setPhotoID and checkValidFormat.
 */
public class PhotoTest extends ActivityInstrumentationTestCase2 {


    private Integer size = 100;
    private String format = "Bitmap";
    private String encodedImage = "This is so encoded";
    private UUID photoID; // = test.fromString("test");

    public PhotoTest() { super (Photo.class); }

    public void testCreatePhoto() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
    }

   /* Tests for Photo class functionality */

    public void testCheckValidSize(){
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertTrue(photo.checkValidSize(photo.getSize()));
        photo.setSize(65548);
        assertFalse(photo.checkValidSize(photo.getSize()));
    }

    public void testCheckValidFormat(){
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertTrue(photo.checkValidFormat(photo.getFormat()));
        photo.setFormat("asdfasdf");
        assertFalse(photo.checkValidFormat(photo.getFormat()));
    }

    /*Test Getters and Setters*/

    public void testGetSize() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertEquals(photo.getSize(), size);
    }

    public void testSetSize() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        photo.setSize(12);
        assertEquals((int) photo.getSize(), 12);
    }

    public void testGetFormat() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertEquals(photo.getFormat(), format);
    }

    public void testSetFormat() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        photo.setFormat("JPG");
        assertEquals(photo.getFormat(), "JPG");
    }

    public void testGetEncodedImage() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertEquals(photo.getEncodedImage(), encodedImage);
    }

    public void testSetEncodedImage() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        photo.setEncodedImage("Even more encoded");
        assertEquals(photo.getEncodedImage(), "Even more encoded");
    }

    public void testGetPhotoID() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        assertEquals(photo.getPhotoID(), photoID);
    }


    public void testSetPhotoID() {
        Photo photo = new Photo(size, format, encodedImage, photoID);
        UUID test = new UUID(0xAAAAAAAA, 0x99999999);
        photo.setPhotoID(test);
        assertEquals(photo.getPhotoID(), test);
    }
}