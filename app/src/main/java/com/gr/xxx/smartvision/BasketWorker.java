package com.gr.xxx.smartvision;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BasketWorker extends Worker {

    public BasketWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Context context = getApplicationContext();

        BasketReminderUtils.sleep();
        BasketReminderUtils.scheduleNotification(context);
        return Result.success();
    }
}
