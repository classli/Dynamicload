package com.sven.dynamicload;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dalvik.system.DexClassLoader;

/**
 * Created by sven on 2016/1/6.
 */
public class LifeCircleController {
    private Activity mProxy;
    private String mPluginClassz;
    private String packageName;
    private PluginApk mPluginApk;
    private PluginActivity mPlugin;
    private Resources mResources;

    public LifeCircleController(Activity mProxy) {
        this.mProxy = mProxy;
    }

    public void onCreate(Bundle bundle) {
        mPluginClassz = bundle.getString("PLUGIN_CLASS_NAME");
        packageName = bundle.getString("PACKAGE_NAME")
        mPluginApk = PluginManager.getInstance().getPluginApk(packageName);
        mPlugin = (PluginActivity) loadPluginable(mPluginApk.classLoader,mPluginClassz);
        mPlugin.attach(mProxy);
        mResources=mPluginApk.resources;
        mPlugin.onCreate(bundle);
    }

    private Object loadPluginable(DexClassLoader classLoader,String classz) {
        Object instance=null;
        try {
            Class<?> localclass = classLoader.loadClass(classz);
            Constructor<?> localConstructor = localclass.getConstructor();
            instance = localConstructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public ClassLoader getClassLoader() {
        return mPluginApk.classLoader;
    }

    public AssetManager getAssets() {
        return mPluginApk.assetManager;
    }

    public Resources getResources() {
        return mResources;
    }

    public PluginActivity getRemoteActivity() {
        return mPlugin;
    }
}
