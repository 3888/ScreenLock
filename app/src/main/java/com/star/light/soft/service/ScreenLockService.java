package com.star.light.soft.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.star.light.soft.R;
import com.star.light.soft.utils.ScreenUtils;

public class ScreenLockService extends Service {

    private FrameLayout topView;
    private WindowManager windowManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        initScreenUtils();
        initViews();
    }

    private void initViews() {
        topView = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.screen_lock_view, null);

        WindowManager.LayoutParams topParams = new WindowManager.LayoutParams(
                ScreenUtils.width,
                ScreenUtils.height,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        topParams.x = 0;
        topParams.y = 0;
        topParams.gravity = Gravity.TOP | Gravity.END;
        windowManager.addView(topView, topParams);
    }

    private void initScreenUtils() {
        final Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ScreenUtils.width = size.x;
        ScreenUtils.height = size.y;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (topView != null) windowManager.removeView(topView);
    }
}