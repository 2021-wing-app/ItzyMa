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

public class MainActivity extends AppCompatActivity implements OnTabItemSelectedListener {
    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    Fragment1 fragment1;
    Fragment2 fragment2;

    HomeworkDatabase homeworkDatabase;

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

<<<<<<< Updated upstream
=======

    @Override
    public void insert(String deadline, String subjectName, String homeworkName, String alarm_time) {
        homeworkDatabase.insertRecord(deadline, subjectName, homeworkName, alarm_time);
        Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public ArrayList<Homework> selectAll() {
        ArrayList<Homework> homeworkArrayList = homeworkDatabase.selectAll();
        Toast.makeText(getApplicationContext(), "조회 완료!", Toast.LENGTH_SHORT).show();
        return homeworkArrayList;
    }

>>>>>>> Stashed changes
    @Override
    public void showFragment2(Homework item) {

        fragment2 = new Fragment2();
        fragment2.setItem(item);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment2).commit();

    }
}