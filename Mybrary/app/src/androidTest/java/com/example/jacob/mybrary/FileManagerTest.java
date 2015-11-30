package com.example.jacob.mybrary;

import android.test.AndroidTestCase;

import java.io.File;
import java.io.IOException;

/**
 * Simple tests for FileManager.
 *
 * Created by Dominic on 2015-11-13.
 */
public class FileManagerTest extends AndroidTestCase {
    public void testWriteFile() {
        FileManager fm = FileManager.getInstance();

        try {
            fm.saveJson("test.json", "{\"val\":2}");
        } catch (IOException e) {
            fail();
        }

        assertTrue(fm.fileExists("test.json"));
        File file = new File(fm.getAppFolderName() + "test.json");
        file.delete();
    }

    public void testReadFile() {
        FileManager fm = FileManager.getInstance();

        try {
            fm.saveJson("test.json", "{\"val\":2}");
            String content = fm.readFile("test.json");
            assertTrue(content.equals("{\"val\":2}"));
        } catch (IOException e) {
            fail();
        } finally {
            File file = new File("test.json");
            file.delete();
        }
    }

    public void testMissingFile() {
        FileManager fm = FileManager.getInstance();

        try {
            String content = fm.readFile("DNE.json");
        } catch (IOException e) {
            return;
        }

        fail();
    }

    public void testEmptyFile() {
        FileManager fm = FileManager.getInstance();

        try {
            fm.saveJson("test.json", "");
            String content = fm.readFile("test.json");
            assertTrue(content.equals(""));
        } catch (IOException e) {
            fail();
        } finally {
            File file = new File("test.json");
            file.delete();
        }
    }
}
