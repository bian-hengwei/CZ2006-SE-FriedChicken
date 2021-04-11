package com.ntu.medcheck.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.ntu.medcheck.controller.NotificationScheduler.NOTIFICATION_CHANNEL_ID;

/**
 * MyNotificationPublisher deals with all receiving notifications on alarm ring.
 * @author He Yinan
 */
public class MyNotificationPublisher extends BroadcastReceiver {

    /**
     * Log tag for debugging.
     */
    private static final String TAG = "MyNotificationPublisher";

    /**
     * Notification id for notification.
     */
    public static String NOTIFICATION_ID = "notification-id";

    /**
     * Notification constant.
     */
    public static String NOTIFICATION = "notification";

    /**
     * Send notification when alarm is received.
     * @param context Context for building intent.
     * @param intent Intent for notification.
     */
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: alarm rang");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        assert notificationManager != null;
        notificationManager.notify(id, notification);
    }
}