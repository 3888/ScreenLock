package com.star.light.soft;

import android.app.Application;

import com.star.light.soft.utils.SharedPref;

import timber.log.Timber;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPref.init(this);
        Timber.plant(new Timber.DebugTree());
    }
}

