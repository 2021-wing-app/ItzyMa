package org.techtown.practice1;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.practice1.Alarm_Reciver;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends FragmentActivity implements OnTabItemSelectedListener, OnDatabaseCallback {
    private static final String TAG = "MainActivity";

    BroadcastReceiver br;
    PendingIntent pending_intent;
    Context context;

    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ThirdFragment thirdFragment;

    HomeworkDatabase homeworkDatabase;

    BottomNavigationView bottomNavigation;

    /**
     * 데이터베이스 인스턴스
     */
    public static HomeworkDatabase mDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, firstFragment).commit();

                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, secondFragment).commit();

                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "세 번째 탭 선택됨", Toast.LENGTH_LONG).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, thirdFragment).commit();

                        return true;
                }

                return false;
            }
        });

        /*
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
         */

        // 1페이지로 이동
        onTabSelected(0);

        // 데이터베이스 열기
        openDatabase();
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 0) {
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            secondFragment = new SecondFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, secondFragment).commit();
        } else if (position == 2) {
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }
    }

    @Override
    public void showThirdFragment(Homework item) {

        thirdFragment = new ThirdFragment();
        thirdFragment.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, thirdFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    public void openDatabase() {
        // open database
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = HomeworkDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Homework database is open.");
        } else {
            Log.d(TAG, "Homework database is not open.");
        }
    }

    private void println(String data) {
        Log.d(TAG, data);
    }

    @Override
    public void insert(String deadline, String subjectName, String homeworkName) {
        homeworkDatabase.insertRecord(deadline, subjectName, homeworkName);
        Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<Homework> selectAll() {
        ArrayList<Homework> homeworkArrayList = homeworkDatabase.selectAll();
        Toast.makeText(getApplicationContext(), "조회 완료!", Toast.LENGTH_SHORT).show();
        return homeworkArrayList;
    }
}