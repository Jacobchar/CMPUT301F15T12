package com.example.jacob.mybrary;

/**
 * Created by Jacob on 2015-11-28.
 * Manager class to deal with adding, deleting, downloading and taking photos
 * of our inventory.
 */
public class PhotoManager {

    private static PhotoManager instance = null;

    protected PhotoManager(){}

    /**
     * Creating as a Singleton
     * @return Returns the singleton instance
     */
    public static PhotoManager getInstance() {
        if(instance == null) {
            instance = new PhotoManager();
        }
        return instance;
    }

    public void savePhoto(){

    }

    public void addPhoto(){

    }

    public void deletePhoto(){

    }

    public void takePhoto(){

    }

    public Photo downloadPhoto(String path){
        
    }

}
