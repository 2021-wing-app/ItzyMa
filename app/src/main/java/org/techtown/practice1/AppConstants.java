package org.techtown.practice1;

import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;

public class AppConstants {

    public static final int MODE_INSERT = 1;
    public static final int MODE_MODIFY = 2;


    private static Handler handler = new Handler();
    private static final String TAG = "AppConstants";
    public static void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, data);
            }
        });
    }


}
