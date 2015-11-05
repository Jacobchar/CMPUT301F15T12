package com.example.jacob.mybrary;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Ben on 05/11/2015.
 */
public class ProfileController {

    public ProfileController() {
    }

    public void updateUser(User user, Context context){
        saveUserInFile(user, context);
    }

    public void saveUserInFile(User user, Context activity){
        try {
            FileOutputStream fos = activity.openFileOutput("USER.txt", 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}
