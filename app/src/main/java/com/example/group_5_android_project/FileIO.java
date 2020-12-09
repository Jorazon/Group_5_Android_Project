package com.example.group_5_android_project;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.content.ContextCompat;

import com.google.android.material.internal.ContextUtils;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

//https://developer.android.com/training/data-storage/app-specific
//https://stackoverflow.com/questions/12158483/how-to-write-an-arraylist-to-file-and-retrieve-it

public class FileIO {

    public static boolean saveToFile(Context context, List<?> list, String fileName){
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static List<?> readFromFile(Context context, String fileName){
        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<?> result = (List<?>) ois.readObject();
            ois.close();
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
