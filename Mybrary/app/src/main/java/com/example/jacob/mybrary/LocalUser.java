package com.example.jacob.mybrary;

/**
 * Created by Dominic on 2015-11-09.
 *
 * LocalUser represents the user registered on the device.
 */
public class LocalUser extends User {
    private static LocalUser ourInstance = new LocalUser();

    public static LocalUser getInstance() {
        return ourInstance;
    }

    private LocalUser() {
        super("", "", "", "", "", "");
    }
}
