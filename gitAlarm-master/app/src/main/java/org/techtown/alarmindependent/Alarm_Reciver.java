package org.techtown.alarmindependent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Alarm_Reciver extends BroadcastReceiver {

    Context context;
    Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {

        //기존 코드
        this.context=context;
        long time = intent.getExtras().getLong("time");

        //AlarmService로 보내는 intent
        Intent br_intent=new Intent(context,AlarmService.class);

        br_intent.putExtra("time", time);

        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(br_intent);
        }else {
            this.context.startService(br_intent);
        }


    }
}
