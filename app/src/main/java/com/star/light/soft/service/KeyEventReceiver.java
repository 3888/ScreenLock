package com.star.light.soft.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

import timber.log.Timber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.star.light.soft.Constants.BROADCAST_SCREEN_ACTION;

public class KeyEventReceiver extends BroadcastReceiver {
    private Intent service;
    private static boolean isLocked = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.i("onReceive %s", intent.getAction());

        if (Objects.equals(intent.getAction(), BROADCAST_SCREEN_ACTION)) {
            if (!isLocked) {
                service = new Intent(context, ScreenLockService.class);
                service.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startService(service);
                isLocked = true;
            } else {
                context.stopService(service);
                isLocked = false;
            }
        }
    }
}