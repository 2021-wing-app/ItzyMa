package org.techtown.practice1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.techtown.practice1.AlarmReceiver;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener {
    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    static int i = 1;
    public static Context Context;

    Fragment1 fragment1;
    Fragment2 fragment2;

    HomeworkDatabase homeworkDatabase;
    PendingIntent pendingIntent;

    // 알람 기능에 필요한 클래스 객체들
    private AlarmManager alarmManager;
    private GregorianCalendar mCalender;
    private NotificationManager notificationManager;

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
        alert_ex.setTitle("과제 파이팅하세요!");
        AlertDialog alert = alert_ex.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Context = this;
        mCalender = new GregorianCalendar();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("목록"));
        tabs.addTab(tabs.newTab().setText("추가"));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택한 탭: " + position);

                Fragment selected = null;
                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                }
                // 선택된 프래그먼트로 전환
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // open database
        if (homeworkDatabase != null) {
            homeworkDatabase.close();
            homeworkDatabase = null;
        }

        homeworkDatabase = homeworkDatabase.getInstance(this);
        boolean isOpen = homeworkDatabase.open();
        if (isOpen) {
            Log.d(TAG, "데이터베이스가 열림");
        } else {
            Log.d(TAG, "데이터베이스가 닫힘");
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (homeworkDatabase != null) {
            homeworkDatabase.close();
            homeworkDatabase = null;
        }
    }

    @Override
    public void showFragment2(Homework item, int pos) {

        fragment2 = new Fragment2();
        fragment2.setItem(item);

        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos); // Key, Value

        fragment2.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment2).commit();

    }



    public void setAlarm(String form) {
        //AlarmReceiver에 값 전달
        Intent receiverIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, i, receiverIntent, 0);

        //String yeah = "2021-09-05 17:50"; //임의로 날짜와 시간을 지정

        Toast.makeText(getApplicationContext(), form, Toast.LENGTH_LONG).show();

        //날짜 포맷을 바꿔주는 소스 코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateTime = null;
        try {
            dateTime = dateFormat.parse(form);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);

        i++;  // i 값을 1 증가
    }

    public void removeNotification(int pos){

        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, pos+1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (sender != null) {
            am.cancel(sender);
            sender.cancel();
        }

        Toast.makeText(getApplicationContext(), pos+"", Toast.LENGTH_LONG).show();

        // NotificationManagerCompat.from(this).cancel(pos+1);
        //notificationManager.cancel(pos+1); // cancel(알림 특정 id)
    }
}