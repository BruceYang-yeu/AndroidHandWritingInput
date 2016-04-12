package com.gdbbk.dw.handwritinginput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

/**
 * Created by Administrator on 16.4.6.
 */
public class AssetInstaller {
    private Context context;
    private static final String TAG = "BBK_AssetInstaller";
    private String zipName;


    public AssetInstaller(Context context, String zipName) {
        this.context = context;
        this.zipName = zipName;
    }

    private void copyAssets() {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e(TAG, "Failed to get asset file list.", e);
        }
        Log.d(TAG, "[" + Thread.currentThread().getStackTrace()[2].getFileName() + "," + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]");
        for (String filename : files) {
            Log.d(TAG, "[" + Thread.currentThread().getStackTrace()[2].getFileName() + "," + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]" + files.toString());
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                Log.d(TAG, "file list: " + filename.toString());
                File cacheDir = new File(globalvariable.zipFileDir);
                cacheDir.mkdir();
                File cacheFile = new File(cacheDir, filename);
                cacheFile.createNewFile();
                out = new FileOutputStream(globalvariable.zipFileDir + "/" + filename);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
            } catch (IOException e) {
                Log.e(TAG, "Failed to copy asset file: " + filename, e);
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private void explodeAsset() throws IOException {
        String zipPath = globalvariable.zipFileDir + "/" + zipName + ".zip";
        String extractPath = globalvariable.zipFileDirExact + "/";
        File file = new File(zipPath);
        ZipFile zipFile = new ZipFile(file);
        try {
            Zip _zip = new Zip(zipFile);
            _zip.unzip(extractPath);
            _zip.close();
            file.delete();
        } catch (IOException ie) {
            Log.e(TAG, "failed extraction", ie);
        }
    }


    private boolean dirCheck() {
        /*String state = Environment.getExternalStorageState();
        File filesDir;
        // Make sure it's available
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            filesDir =  context..getExternalStorageDirectory(null);
        } else {
            // Load another directory, probably local memory
            filesDir = context.getFilesDir();
        }
        */
        String path = globalvariable.zipFileDirExact + "/" + zipName;
        Log.d(TAG, "[ " + Thread.currentThread().getStackTrace()[2].getFileName() + "," + Thread.currentThread().getStackTrace()[2].getLineNumber() + " ]  " + path);
        File dir = new File(path);
        if (!dir.exists() && !dir.isDirectory()) {
            return false;
        } else {
            return true;
        }
        //return dir.exists();
    }

    public void execute() throws IOException {
        if (!dirCheck()) {
            copyAssets();
            Log.d(TAG, "[" + Thread.currentThread().getStackTrace()[2].getFileName() + "," + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]");
            explodeAsset();
            Log.d(TAG, "installed packages.");
        } else {
            Log.d(TAG, "already installed");
        }
    }
}
