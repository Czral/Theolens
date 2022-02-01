package com.gr.xxx.smartvision;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.gr.xxx.smartvision.activities.BasketActivity;

import static android.app.Notification.*;

public class BasketReminderUtils {

    private static final int NOTIFICATION_ID = 15;
    private static final String CHANNEL_NOTIFICATION = "notification_channel_one";
    private static final String CHANNEL_ID = "channel_id";
    private static final String FIRST_CHANNEL_FOR_NOTIFICATION = "channel_for_notification";
    private static NotificationManager notificationManager;

    public static void scheduleNotification(Context context) {

        notificationManager = context.getApplicationContext().getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NOTIFICATION, importance);
            channel.setDescription(FIRST_CHANNEL_FOR_NOTIFICATION);
            notificationManager.createNotificationChannel(channel);
        }

        String sSound = "android.resource://" + context.getPackageName() + "/" + R.raw.notification_sound;

        Intent openAppIntent = new Intent(context, BasketBroadcastReceiver.class);
        openAppIntent.setAction(Constants.IGNORE);
        openAppIntent.putExtra(Constants.BASKET_NOTIFICATION_ID, 200);

        Intent emptyBasketIntent = new Intent(context, BasketActivity.class);
        emptyBasketIntent.setAction(Constants.EMPTY_BASKET);
        emptyBasketIntent.putExtra(Constants.BASKET_NOTIFICATION_ID, 300);
        emptyBasketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent appPendingIntent = PendingIntent.getBroadcast(context, 200, openAppIntent, 0);
        PendingIntent emptyBasketPendingIntent = PendingIntent.getActivity(context, 300, emptyBasketIntent, 0);

        Action.Builder builder = new Action.Builder(R.drawable.ic_baseline_cancel, context.getResources().getString(R.string.ignore_basket_notification), appPendingIntent);
        Action action = builder.build();

        Builder notificationBuilder = new Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.basket_not_empty_notification))
                .setSound(Uri.parse(sSound))
                .addAction(action)
                .setContentIntent(emptyBasketPendingIntent)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();
        notification.flags |= FLAG_AUTO_CANCEL;

        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public static void cancelNotification(Context context) {
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    // Sleep for 1 hour
    public static void sleep() {
        try {
            Thread.sleep(1000 * 60 * 60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
