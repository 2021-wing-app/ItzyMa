package org.techtown.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void setAlarm(){
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("no", alarmldx);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent=getBroadcast(context, alarmldx, intent,0);

        //시간 불러오기(라디오그룹과의 연동 필요. 임시로 시간 입력받는 것으로 만들었음)
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        switch(month){
            case 1:
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case 2:
                calendar.set(Calendar.MONTH,calendar.FEBURARY);
                break;
            case 3:
                calendar.set(Calendar.MONTH,calendar.MARCH);
                break;
            case 4:
                calendar.set(Calendar.MONTH,calendar.APRIL);
                break;
            case 5:
                calendar.set(Calendar.MONTH,calendar.MAY);
                break;
            case 6:
                calendar.set(Calendar.MONTH,calendar.JUNE);
                break;
            case 7:
                calendar.set(Calendar.MONTH,calendar.JULY);
                break;
            case 8:
                calendar.set(Calendar.MONTH,calendar.AUGUST);
                break;
            case 9:
                calendar.set(Calendar.MONTH,calendar.SEPTEMBER);
                break;
            case 10:
                calendar.set(Calendar.MONTH,calendar.OCTOBER);
                break;
            case 11:
                calendar.set(Calendar.MONTH,calendar.NOVEMBER);
                break;
            case 12:
                calendar.set(Calendar.MONTH,calendar.DECEMBER);
                break;
        }
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long aTime = System.currentTimeMillis();
        long bTime = calendar.getTimeInMillis();

        long interval = 1000 * 60 * 60  * 24;

        while(aTime>bTime){
            bTime += interval;
        }

        alarmManager.setRepeating(AlarmManager.RTC, bTime, interval, pendingIntent);

    }

    public void cancelAlarm(){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmUserViewActivty.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmIdx, intent, 0);
        alarmManager.cancel(pendingIntent);

    }

//요일 구분
public class AlarmReceiver extends BroadcastReceiver{

        @Override
    public void onReceive(Context context, Intent intent){
            final int no = intent.getInExtra("no", -1);
            WritingInfo wi = new WritingInfo();

            if(no==-1){
                Log./("Error", "AlarmReceiver intent error");
                return;
            }

            final String[] week = {"일", "월", "화", "수", "목", "금", "토"};
            ArrayList<AlarmInfoV0> alarmInfo = wi.selectAlarmInfo(context, no);
            Calendar calendar = Calendar.getInstance();
            String todayDayOfWeek = week[calendar.get(Calendar.DAY_OF_WEEK)-1];
            String[] alarmDayOfWeek = alarmInfo.get(0).getDayOfWeek().spilt("/");
            int useOrNot = alarmInfo.get(0).getUseOrNot();

            boolean weekCheck = false;

            for(int i=0; i<alarmDayOfWeek.length;i++){
                if(alarmDayOfWeek[i].equals(todayDayOfWeek)){
                    weekCheck=true;
                    break;
                }
            }

            if(!weekCheck){
                return;
            }
            else if(weekCheck){
                if(useOrNot ==1){
                    Intent activityIntent = new Intent(context,AlarmUserViewActivity.class);
                    activityIntent.putExtra("no", alarmInfo.get(0).getListNo());
                    activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(activityIntent);
                }
            }
        }
    }
}