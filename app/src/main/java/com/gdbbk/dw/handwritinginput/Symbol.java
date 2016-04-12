package com.gdbbk.dw.handwritinginput;

import java.util.List;

import android.graphics.PointF;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by Administrator on 16.4.6.
 */
public class Symbol {
    private Stroke[] _strokes;
    private String _character;
    private static final String TAG = "BBK_Symbol";

    public Symbol(Stroke[] strokes, String character) {
        Log.d(TAG, "[" + Thread.currentThread().getStackTrace()[2].getFileName() + "," + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]");
        _strokes = strokes;
        _character = character;
    }

    public Stroke[] getStrokes() {
        return _strokes;
    }

    public String getCharacter() {
        return _character;
    }
}
