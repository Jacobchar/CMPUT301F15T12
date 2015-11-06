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

    public static GsonManager getInstance() {
        if(instance == null) {
            instance = new GsonManager();
        }
        return instance;
    }

    public String toJson(Object object){

        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public Type fromJson(JsonElement json, Type typeOfT){

        Gson gson = new Gson();
        return gson.fromJson(json, typeOfT);

    }

}
