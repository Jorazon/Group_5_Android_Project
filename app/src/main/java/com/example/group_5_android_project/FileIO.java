package com.example.group_5_android_project;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

//https://developer.android.com/training/data-storage/app-specific
//https://stackoverflow.com/questions/12158483/how-to-write-an-arraylist-to-file-and-retrieve-it

/**
 * Class for reading and writing objects to app-specific internal storage.
 * @author Micael Oksiala
 */
public class FileIO {

    /**
     * Saves an object to a specified file.
     * @param context passed activity context
     * @param object object to save
     * @param fileName file path to save to
     * @return true or false based on if file was saved successfully or not
     */
    public static boolean saveToFile(Context context, Object object, String fileName){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Reads an object form a specified file.
     * @param context passed activity context
     * @param fileName file path to read from
     * @return read object or null if reading wasn't successful
     */
    public static Object readFromFile(Context context, String fileName){
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object result = ois.readObject();
            ois.close();
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves time to SharedPreferences.
     * @param context passed activity context
     * @param millis time in milliseconds
     */
    public static void saveMillis(Context context, long millis){
        SharedPreferences preferences = context.getSharedPreferences("MyActivity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("millis",millis);
        editor.apply();
    }

    /**
     * Loads time form SharedPreferences.
     * @param context passed activity context
     * @return loaded time epoch or current date if time hasn't been saved before
     */
    public static long loadMillis(Context context){
        SharedPreferences preferences = context.getSharedPreferences("MyActivity", Context.MODE_PRIVATE);
        return preferences.getLong("millis", new Date().getTime());
    }
}
