package com.mx.cherry.inventario.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PreferencesUtil {

    private Context context;
    private SharedPreferences prefs;
    private String prefsFileName;

    public PreferencesUtil(Context context, String prefsFileName) {
        this.context = context;
        this.prefsFileName = prefsFileName;

    }

    public void setStringPrefrences(String prefName, String Value) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putString(prefName, Value);
        editor.apply();
    }

    public String getStringPrefrences(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        if (prefs.contains(prefName))
            return prefs.getString(prefName, null);
        else
            return "";
    }

    public void setIntPrefrences(String prefName, int Value) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putInt(prefName, Value);
        editor.apply();
    }

    public int getIntPrefrences(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        return prefs.getInt(prefName, 0);
    }

    public void setBooleanPrefrences(String prefName, Boolean Value) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putBoolean(prefName, Value);
        editor.apply();
    }


    public boolean getBooleanPrefrences(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        return prefs.getBoolean(prefName, false);
    }

    public void setFloatPrefrences(String prefName, Float Value) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putFloat(prefName, Value);
        editor.apply();
    }


    public float getFloatPrefrences(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        return prefs.getFloat(prefName, 0);
    }

    public void setLongPrefrences(String prefName, Long Value) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.putLong(prefName, Value);
        editor.apply();
    }

    public long getLongPrefrences(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        return prefs.getLong(prefName, 0);
    }


    public void removeAllPrefrences() {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public void deletePreference(String prefName) {
        prefs = context.getSharedPreferences(prefsFileName, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();
        editor.remove(prefName);
        editor.apply();
    }

}
