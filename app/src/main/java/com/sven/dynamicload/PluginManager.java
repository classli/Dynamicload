package com.sven.dynamicload;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by sven on 2016/1/6.
 */
public class PluginManager {

    private static Context mContext;

    private Map<String, PluginApk> sMap = new HashMap<String, PluginApk>();


    private PluginManager() {
    }

    private static class PluginMgrHolder {
        private static PluginManager sManager = new PluginManager();
    }

    public static PluginManager getInstance() {
        return PluginMgrHolder.sManager;
    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public PluginApk getPluginApk(String packageName) {
        return sMap.get(packageName);
    }

    public void loadApk(String apkPath) {
        PackageInfo packageInfo = queryPackageInfo(apkPath);
        if (packageInfo == null || TextUtils.isEmpty(packageInfo.packageName)) {
            throw new NullPointerException("packageInfo is not found");
        }

        PluginApk pluginApk = sMap.get(packageInfo.packageName);
        if (pluginApk == null) {
            pluginApk = createApk(apkPath);
            if (pluginApk != null) {
                pluginApk.packageInfo = packageInfo;
                pluginApk.packageName = packageInfo.packageName;
                sMap.put(packageInfo.packageName, pluginApk);
                Log.e("mar",packageInfo.packageName);
            } else {
                Log.e("mar","mar2");
                throw new NullPointerException("pluginApk is null");
            }
        }
    }

    private PluginApk createApk(String apkApk) {
        PluginApk pluginApk = null;

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkApk);
            Resources pluginRes = new Resources(assetManager,
                    mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
            pluginApk = new PluginApk(pluginRes);
            pluginApk.classLoader = createDexClassLoader(apkApk);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApk;
    }


    private DexClassLoader createDexClassLoader(String path) {
        File dexOutPutDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        DexClassLoader loader = new DexClassLoader(path,
                dexOutPutDir.getAbsolutePath(), null, mContext.getClassLoader());
        Log.e("plugman", dexOutPutDir.getAbsolutePath());
        return loader;
    }

    private PackageInfo queryPackageInfo(String apkPath) {

        return mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_CONFIGURATIONS | PackageManager.GET_SERVICES);
    }

    public void startActivity(Intent intent) {
        Intent mIntent = new Intent(mContext, ActivityProxy.class);
        Bundle extra = intent.getExtras();
        if (extra == null
                || !extra.containsKey("PLUGIN_CLASS_NAME")
                && !extra.containsKey("PACKAGE_NAME")) {
            throw new IllegalArgumentException("Not have set the plugin path and name");
        }
        mIntent.putExtras(intent);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
    }

}
