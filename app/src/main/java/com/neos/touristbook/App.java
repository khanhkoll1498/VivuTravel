package com.neos.touristbook;

import android.app.Application;
import android.graphics.Typeface;

public class App extends Application {
    private static App instance;
    private Typeface fontBold, fontThin, fontRegular, fontLight;
    private StorageCommon storageCommon;

    public StorageCommon getStorageCommon() {
        return storageCommon;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storageCommon = new StorageCommon();
        init();
    }

    private void init() {
        fontBold = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Bold.ttf");
        fontLight = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Light.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Regular.ttf");
        fontThin = Typeface.createFromAsset(getAssets(),
                "font/Roboto-Thin.ttf");
    }

    public Typeface getFontBold() {
        return fontBold;
    }

    public Typeface getFontThin() {
        return fontThin;
    }

    public Typeface getFontRegular() {
        return fontRegular;
    }

    public Typeface getFontLight() {
        return fontLight;
    }

    public static App getInstance() {
        return instance;
    }
}
