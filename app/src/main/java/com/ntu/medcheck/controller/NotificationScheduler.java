package com.ntu.medcheck.controller;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ntu.medcheck.R;
import com.ntu.medcheck.view.HomeActivity;

/**
 * Notification scheduler class use alarms to schedule notifications and cancel notifications.
 */
public class NotificationScheduler {

    /**
     * Log tag for debugging.
     */
    private static final String TAG = "NotificationScheduler";

    /**
     * Default channel id.
     */
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    /**
     * Default notification channel.
     */
    private final static String default_notification_channel_id = "default";

    /**
     * Schedules notification at delay milliseconds later.
     * @param notification Notification set.
     * @param delay Time to set notification.
     * @param context Context of pending intent.
     * @param repeat If repeat daily.
     * @param id Unique id for notification.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scheduleNotification(Notification notification, long delay, Context context, boolean repeat, int id) {
        Log.d(TAG, "scheduleNotification: scheduling notification");
        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, id);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        if (repeat) {
            Log.d(TAG, "scheduleNotification: repeat is on");
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else {
            Log.d(TAG, "scheduleNotification: repeat is off");
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent);
        }
    }

    /**
     * Cancel notification with id.
     * @param context Context of pending intent.
     * @param id Unique id of notification.
     */
    public void cancelNotification(Context context, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyNotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    /**
     * Build a notification and return.
     * @param content Content of notification.
     * @param title Title of notification.
     * @param context Context of pending intent.
     * @param id Unique id of notification.
     * @return A notification.
     */
    public Notification getNotification(String content, String title, Context context, int id) {
        Intent resultIntent = new Intent(context, HomeActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, id, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.icon_tight);
        builder.setAutoCancel(true);
        builder.setContentIntent(resultPendingIntent);
        builder.setChannelId("10001");
        return builder.build();
    }
}
