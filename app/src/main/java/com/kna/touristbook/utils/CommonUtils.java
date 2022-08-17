package com.kna.touristbook.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.kna.touristbook.App;
import com.kna.touristbook.view.event.OnActionCallback;
import com.kna.touristbook.view.event.OnCommonCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtils {

    private static final String FILE_SETTING = "setting.pref";
    private static final String TAG = CommonUtils.class.getName();
    public static final String KEY_LOAD_ANIM_END = "KEY_LOAD_ANIM";
    private static CommonUtils instance;

    private CommonUtils() {
    }


    public static CommonUtils getInstance() {
        if (instance == null) {
            instance = new CommonUtils();
        }
        return instance;
    }

    public void loadAnim(View view, int anim_file) {
        Animation anim = AnimationUtils.loadAnimation(App.getInstance(), anim_file);
        view.startAnimation(anim);
    }

    public void loadAnim(View view, int anim_file, final OnActionCallback callBack) {
        Animation anim = AnimationUtils.loadAnimation(App.getInstance(), anim_file);
        anim.setAnimationListener(new OnCommonCallback() {
            @Override
            public void onAnimationEnd(Animation animation) {
                callBack.callback(KEY_LOAD_ANIM_END, null);
            }
        });
        view.startAnimation(anim);
    }

    public void toast(String text) {
        Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    public void log(String text) {
        Log.d(TAG, text);
    }


    public String readFileFromAssets(String fileName) {
        String data = "";
        try {
            InputStream fin = App.getInstance().getAssets().open(fileName);
            byte buff[] = new byte[1024];
            int len = fin.read(buff);
            while (len > 0) {
                data += new String(buff, 0, len);
                len = fin.read(buff);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public InputStream getFileFromAssets(String name) {
        try {
            return App.getInstance().getAssets().open(name);
        } catch (IOException e) {
        }
        return null;
    }

    public void saveFileToSystemStorage(File file, String nameFile) {
        String savePath =
                Environment.getDataDirectory().toString() + "/data/" + App.getInstance().getPackageName();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
        }
        saveFile(fin, savePath, nameFile);
    }


    public void saveFile(InputStream fin, String savePath, String nameFile) {
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(new File(savePath + "/" + nameFile));
            int lenght = 0;
            byte buff[] = new byte[1024];
            lenght = fin.read(buff);
            while (lenght > 0) {
                fout.write(buff, 0, lenght);
                lenght = fin.read(buff);
            }
            fin.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void saveFileToStorage(File file, String folder, String nameFile) {
        String savePath =
                App.getInstance().getExternalFilesDir(null).getPath();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        saveFile(fin, savePath, nameFile);
    }


    public void savePref(String key, String value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE);
        pref.edit().putString(key, value).apply();
    }

    @SuppressLint("WrongConstant")
    public void savePref(String key, String value, boolean isOverride) {
        SharedPreferences pref;
        if (!isOverride) {
            pref = App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE);
        } else {
            pref = App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_APPEND);
        }
        pref.edit().putString(key, value).apply();
    }

    public String getValuePref(String key) {
        return App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE)
                .getString(key, "");
    }

    public String getValuePref(String key, String defaultValue) {
        return App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public void clearPref() {
        SharedPreferences pref = App.getInstance().getSharedPreferences(FILE_SETTING, Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }


    public String formatTime(long duration) {
        return formatTime(duration, true);
    }

    public String formatTime(long duration, boolean isHour) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        if (!isHour) {
            formatter = new SimpleDateFormat("mm:ss");
        }
        Date date = new Date(duration);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public String formatTimeToDate(long duration) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        Date date = new Date(duration);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }
}
