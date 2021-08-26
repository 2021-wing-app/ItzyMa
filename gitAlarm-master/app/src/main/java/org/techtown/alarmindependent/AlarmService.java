package org.techtown.alarmindependent;


import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.Nullable;

public class AlarmService extends Service {

    MediaPlayer mediaPlayer;

    public AlarmService() {
    }

    /*
    @Override
    public void onReceive(Context context, Intent intent{
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            onCreate();
        }
    }
     */

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        if(Build.VERSION.SDK_INT>=26) {
            super.onCreate();
            mediaPlayer = MediaPlayer.create(this, R.raw.ouu);
            mediaPlayer.setLooping(true);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
    }
}