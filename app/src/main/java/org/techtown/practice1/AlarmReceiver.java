package org.techtown.practice1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import org.techtown.practice1.MainActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }

    NotificationManager manager;
    NotificationCompat.Builder builder;

    // 채널을 설정해줘야 Notification이 작동함
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    @Override
    public void onReceive(Context context, Intent intent) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            builder = null;
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(
                        new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                );
                builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                builder = new NotificationCompat.Builder(context);
            }

            // 알림창 클릭 시 activity 화면 부름
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, Fragment1.id, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            // 알림창 제목
            builder.setContentTitle("과제 하자!");
            // 알림창 아이콘
            builder.setSmallIcon(R.drawable.ic_launcher_background);
            // 알림창 터치시 자동 삭제
            builder.setAutoCancel(true);

            builder.setContentIntent(pendingIntent);

            Notification notification = builder.build();
            manager.notify(Fragment1.id, notification);
    }
}

