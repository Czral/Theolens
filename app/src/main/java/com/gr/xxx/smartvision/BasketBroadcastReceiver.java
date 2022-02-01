package com.gr.xxx.smartvision;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BasketBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        try {

            if (action.equals(Constants.IGNORE)) {

                BasketReminderUtils.cancelNotification(context);
            }
        } catch (NullPointerException e) {

            e.printStackTrace();
        }

    }

}

