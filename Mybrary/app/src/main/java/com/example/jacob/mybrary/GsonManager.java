package com.example.jacob.mybrary;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Jacob on 2015-11-04.
 */
public class GsonManager {

    private static GsonManager instance = null;

    protected GsonManager(){}

    /**
     * Creating as a Singleton
     * @return
     */
    public static GsonManager getInstance() {
        if(instance == null) {
            instance = new GsonManager();
        }
        return instance;
    }

    private Gson gson = new Gson();

    /**
     * converts an object to a Json string
     * @param object the object to be converted
     * @return
     */
    public String toJson(Object object){
        return gson.toJson(object);
    }

    /**
     * converts a json Element back into an object
     * @param json element to be converted
     * @param typeOfT Object type
     * @return
     */
    public Type fromJson(JsonElement json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }

}
