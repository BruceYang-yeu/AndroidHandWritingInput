package com.gdbbk.dw.handwritinginput;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 16.4.6.
 */
public class globalvariable {
    public static String strokeXY ="";
    public static String Result="";
    public static Canvas canvasvar=null;
    public static Paint paint=null;
    public static int VSG=0;
    public static boolean TimerFlag=true;
    public static boolean LongPressFlag=true;
    public static boolean ChoiceFlag=true;
    public static int SwipeCount=0;
    public static boolean IsUserWriting=true;
    public static int noOfResults=5;
    public static int ResultCount=1;
    public static boolean isFirststroke = true;
    public static String  zipFileDir = "/storage/sdcard0/Android/data";
    public static String  zipFileDirExact = "/storage/sdcard0/Android/data/handwriting";
    public static String PUBLIC_LOGTAG = "["+ Thread.currentThread().getStackTrace()[2].getFileName()+"_"+Thread.currentThread().getStackTrace()[2].getLineNumber() + "_" + Thread.currentThread().getStackTrace()[2].getMethodName() + "]";
}
