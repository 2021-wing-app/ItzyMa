package org.techtown.practice1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import org.techtown.practice1.Alarm_Reciver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br;
    PendingIntent pending_intent;
    Context context;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("정말로 종료하시겠습니까?");

        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert_ex.setTitle("훌륭히 해내시리라 믿고있어요 :)");
        AlertDialog alert = alert_ex.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        final Intent my_intent = new Intent(this.context, Alarm_Reciver.class);

        final Calendar cal = Calendar.getInstance();

        //임의로 고정(입력받는 식으로 고정 필요)
        cal.set(Calendar.HOUR_OF_DAY,11);
        cal.set(Calendar.MINUTE,14);
        cal.set(Calendar.SECOND,0);

        //intent에 time넘겨주는거 맞음?
        long time = cal.getTimeInMillis();
        my_intent.putExtra("time", time);

        this.pending_intent = PendingIntent.getBroadcast(MainActivity.this,0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //시간적 여유가 있으면 alarmManager 아닌 FCM 이용
        AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm_manager.set(AlarmManager.RTC_WAKEUP, time, pending_intent);

        br = new Alarm_Reciver();
        //ACTION_BOOT_COMPLETED맞는지 확인(표준 브로드캐스트 액션) or ACTION_TIME_CHANGED ? or ALARM_START?
        IntentFilter intent_filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(br,intent_filter);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FirstFragment fragment1 = new FirstFragment();
        transaction.replace(R.id.container, fragment1);
        transaction.commit();
    }
}