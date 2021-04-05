package com.ntu.medcheck.controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.ntu.medcheck.R;

public class ReminderBroadcast extends BroadcastReceiver {

    String channedId = "CHANNEL ID";
    String contentTitle = "TITLE OF NOTIFICATION";
    String contentText = "TEXT OF NOTIFICATION";
    int id = 1000; // each notification has an unique id


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channedId)
                .setSmallIcon(R.drawable.icon_tight)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(id, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(Context context) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CHARSEQUENCE NAME";
            String description = "@@description@@";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channedId + "1", name, importance);
            channel.setDescription(description);
            System.out.println("@@@@@@@@@@@@@@@@@@@Notification channel created");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            System.out.println("@@@@@@@@@@@@@@@@@@@Notification created");
        }
    }
}
