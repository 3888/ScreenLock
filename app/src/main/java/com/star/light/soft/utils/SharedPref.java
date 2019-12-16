package com.star.light.soft.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class SharedPref {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String PREF_NAME = "prefs";

    public static void init(Context context) {
        mContext = context;
    }

    public static void clearPrefs() {
        try {
            SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEdit = prefs.edit();
            prefsEdit.clear();
            prefsEdit.apply();
        } catch (Exception ignored) {
        }
    }

    public static void save(String _key, String _value) {
        if (mContext == null)
            return;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putString(_key, _value);
        prefsEdit.apply();
    }

    public static void save(String _key, Set<String> _value) {
        if (mContext == null)
            return;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putStringSet(_key, _value);
        prefsEdit.apply();
    }

    public static void save(String _key, int _value) {
        if (mContext == null)
            return;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putInt(_key, _value);
        prefsEdit.apply();
    }

    public static void save(String _key, boolean _value) {
        if (mContext == null) {
            return;
        }

        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putBoolean(_key, _value);
        prefsEdit.apply();
    }

    public static String getString(String _key) {
        if (mContext == null)
            return "";
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getString(_key, "");
    }

    public static boolean getBoolean(String _key) {
        if (mContext == null) {
            return false;
        }

        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(_key, false);
    }

    public static boolean getBoolean(String _key, boolean _def) {
        if (mContext == null) {
            return false;
        }

        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(_key, _def);
    }

    public static Set<String> getStringSet(String _key) {
        if (mContext == null)
            return new HashSet<>();

        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getStringSet(_key, new HashSet<>());
    }

    public static int getInt(String _key) {
        if (mContext == null)
            return 0;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(_key, 0);
    }

    public static int getInt(String _key, int _default_value) {
        if (mContext == null)
            return 0;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(_key, _default_value);
    }

    public static long getLong(String _key, long _default_value) {
        if (mContext == null)
            return 0;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getLong(_key, _default_value);
    }

    public static long getLong(String _key) {
        if (mContext == null)
            return 0;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return prefs.getLong(_key, 0L);
    }

    public static void save(String _key, long _value) {
        if (mContext == null)
            return;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();

        prefsEdit.putLong(_key, _value);
        prefsEdit.apply();
    }

    public static void removePrefValue(String key) {
        if (mContext == null) {
            return;
        }
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.remove(key);
        prefsEdit.apply();
    }

    public static boolean isContains(String _key) {
        if (mContext == null)
            return false;
        SharedPreferences prefs = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains(_key);
    }
}
