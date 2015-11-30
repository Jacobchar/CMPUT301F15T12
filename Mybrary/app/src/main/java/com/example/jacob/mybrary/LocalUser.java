package com.example.jacob.mybrary;
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
