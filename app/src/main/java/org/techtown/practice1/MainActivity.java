package org.techtown.practice1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener, OnDatabaseCallback {
    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    Fragment1 fragment1;
    Fragment2 fragment2;

    HomeworkDatabase homeworkDatabase;

    BroadcastReceiver br;
    PendingIntent pending_intent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //알람 수정 내용
        this.context = this;
        final Intent my_intent = new Intent(this.context,Alarm_Reciver.class);

        //임의로 시간 고정해둔 부분. 이 부분을 철환선배 코드랑 연동되도록 수정해야함
        final Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR,cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH,cal.get(Calendar.MONTH));
        cal.set(Calendar.DATE,cal.get(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY,11);
        cal.set(Calendar.MINUTE,14);
        cal.set(Calendar.SECOND,0);

        //set한 시간X 현재 시간이 long으로 변환됨. (수정 필요) > 연동 시 수정 불필
        long time = cal.getTimeInMillis();
        my_intent.putExtra("time",time);

        PendingIntent pending_intent = PendingIntent.getBroadcast(MainActivity.this,0,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        br = new Alarm_Reciver();
        IntentFilter intent_filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(br,intent_filter);
        //알람 코드 내용 끝

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
    public void insert(String title, String author, String contents) {
        homeworkDatabase.insertRecord(title, author, contents);
        Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<Homework> selectAll() {
        ArrayList<Homework> homeworkArrayList = homeworkDatabase.selectAll();
        Toast.makeText(getApplicationContext(), "조회 완료!", Toast.LENGTH_SHORT).show();
        return homeworkArrayList;
    }

    public void showThirdFragment(Homework item) {

        fragment2 = new Fragment2();
        fragment2.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment2).commit();

    }
}