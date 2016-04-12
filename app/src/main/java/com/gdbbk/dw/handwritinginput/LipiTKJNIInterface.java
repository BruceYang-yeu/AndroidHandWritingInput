package com.gdbbk.dw.handwritinginput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import android.util.Log;

/**
 * Created by Administrator on 16.4.6.
 */
public class LipiTKJNIInterface {
    private String _lipiDirectory;
    private String _project;
    private static final String TAG = "BBK_LipiTKJNIInterface";

    static {
        try {
            //System.out.println(System.getProperty("java.library.path"));
            Log.d(TAG, "Java.library.path: " + System.getProperty("java.library.path"));
            System.loadLibrary("handwriting_jni");
            //System.load("/system/lib/handwriting_jni.so");
        } catch (Exception ex) {
            Log.d(TAG, "Exception in getSymbolName Function" + ex.getMessage());
        }
    }

    //  Initializes the interface with a directory to look for projects in
    //  	the name of the project to use for recognition, and the name
    //  	of the ShapeRecognizer to use.
    public LipiTKJNIInterface(String lipiDirectory, String project) {
        _lipiDirectory = lipiDirectory;
        _project = project;
    }

    public String getSymbolName(int id, String project_config_dir) {
        Log.d(TAG, "["+Thread.currentThread().getStackTrace()[2].getFileName()+","+Thread.currentThread().getStackTrace()[2].getLineNumber()+"]" + Thread.currentThread().getStackTrace()[2].getMethodName());
        String line;
        int temp;
        String[] splited_line = null;
        try {
            File map_file = new File(project_config_dir + "unicodeMapfile_alphanumeric.ini");
            BufferedReader readIni = new BufferedReader(new FileReader(map_file));
            readIni.readLine();
            readIni.readLine();
            readIni.readLine();
            readIni.readLine();
            while ((line = readIni.readLine()) != null) {
                splited_line = line.split(" ");
                Log.d(TAG, "split 0=" + splited_line[0]);
                Log.d(TAG, "split 1=" + splited_line[1]);
                splited_line[0] = splited_line[0].substring(0, splited_line[0].length() - 1); //trim out = sign
                Log.d(TAG, "split 0=" + splited_line[0]);
                Log.d(TAG, "Integer id To string: " + (new Integer(id)).toString().toString());
                if (splited_line[0].equals((new Integer(id)).toString())) {
                    splited_line[1] = splited_line[1].substring(2);
                    Log.d(TAG, "splited_line[1] " + splited_line[1]);
                    temp = Integer.parseInt(splited_line[1], 16);
                    Log.d(TAG, "temp " + temp);
                    return String.valueOf((char) temp);
                }
            }
        } catch (Exception ex) {
            Log.d(TAG, "Exception in getSymbolName Function" + ex.toString());
            return "-1";
        }
        return "0";
    }

    public void initialize() {
        Log.d(TAG, "["+Thread.currentThread().getStackTrace()[2].getFileName()+","+Thread.currentThread().getStackTrace()[2].getLineNumber()+"]" + Thread.currentThread().getStackTrace()[2].getMethodName());
        initializeNative(_lipiDirectory, _project);
    }

    public LipitkResult[] recognize(Stroke[] strokes) {
        Log.d(TAG, "["+Thread.currentThread().getStackTrace()[2].getFileName()+","+Thread.currentThread().getStackTrace()[2].getLineNumber()+"]" + Thread.currentThread().getStackTrace()[2].getMethodName());
        LipitkResult[] results = recognizeNative(strokes, strokes.length);

        for (LipitkResult result : results)
            Log.d(TAG, "ShapeID = " + result.Id + " Confidence = " + result.Confidence);

        return results;
    }

    // Initializes the LipiTKEngine in native code
    private native void initializeNative(String lipiDirectory, String project);

    // Returns a list of results when recognizing the given list of strokes
    private native LipitkResult[] recognizeNative(Stroke[] strokes, int numJStrokes);

    public String getLipiDirectory() {
        return _lipiDirectory;
    }

}
