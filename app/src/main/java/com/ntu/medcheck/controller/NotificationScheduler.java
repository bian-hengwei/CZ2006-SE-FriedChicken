package com.ntu.medcheck.controller;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.ntu.medcheck.R;
import com.ntu.medcheck.view.HomeActivity;

import java.util.Calendar;

public class NotificationScheduler {


    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;


    public void scheduleNotification (Notification notification , long delay, AppCompatActivity aca, boolean repeat) {
        Intent notificationIntent = new Intent( aca, MyNotificationPublisher. class );
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 );
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification);
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( aca, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT );
        long futureInMillis = SystemClock.elapsedRealtime () + delay;
        AlarmManager alarmManager = (AlarmManager) aca.getSystemService(Context. ALARM_SERVICE );
        assert alarmManager != null;
        if(repeat) {
            Calendar calendar = Calendar.getInstance();
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent);

            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
            System.out.println("*****&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& gonna repeat");

            //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent);
            //alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent);
        }
    }

    public Notification getNotification (String content,String title, AppCompatActivity aca) {
        Intent resultIntent = new Intent(aca, HomeActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(aca, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder( aca, default_notification_channel_id );
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.icon_tight);
        builder.setAutoCancel(true);
        builder.setContentIntent(resultPendingIntent);
        builder.setChannelId( NOTIFICATION_CHANNEL_ID );
        return builder.build();
    }






}
