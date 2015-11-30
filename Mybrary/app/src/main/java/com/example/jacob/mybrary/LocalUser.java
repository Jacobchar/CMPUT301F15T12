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

    /**
     * To be used during initialization. This method should never be called other than at startup.
     * @param instance LocalUser instance for the singleton to be set to.
     */
    public static void setInstance(LocalUser instance) {
        ourInstance = instance;
    }

}
