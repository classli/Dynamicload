package com.sven.dynamicload;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by sven on 2016/1/6.
 */
public class PluginApk {
    public String packageName;
    public Resources resources;
    public DexClassLoader classLoader;
    public PackageInfo packageInfo;
    public AssetManager assetManager;
    public PluginApk(Resources resources) {
        this.resources = resources;
        assetManager = resources.getAssets();
    }
}
