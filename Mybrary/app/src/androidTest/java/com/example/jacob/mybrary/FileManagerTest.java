package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.File;
import java.io.IOException;

/**
 * Created by Dominic on 2015-11-13.
 *
 * Simple tests for FileManager. Still need to test for expected failures.
 */
public class FileManagerTest extends AndroidTestCase {
    public void testWriteFile() {
        FileManager fm = FileManager.getInstance();

        try {
            fm.saveJson("/data/data/com.example.jacob.mybrary/test.json", "{\"val\":2}");
        } catch (IOException e) {
            fail();
        }

        File file = new File("/data/data/com.example.jacob.mybrary/test.json");
        assertTrue(file.exists());
        file.delete();
    }

    public void testReadFile() {
        FileManager fm = FileManager.getInstance();

        try {
            fm.saveJson("/data/data/com.example.jacob.mybrary/test.json", "{\"val\":2}");
            String content = fm.readFile("/data/data/com.example.jacob.mybrary/test.json");
            assertTrue(content.equals("{\"val\":2}"));
        } catch (IOException e) {
            fail();
        }
        File file = new File("/data/data/com.example.jacob.mybrary/test.json");
        file.delete();
    }
}
