package com.sven.dynamicload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class PluginActivity extends Activity implements Pluginable{

    private Activity mProxyActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void attach(Activity proxyActivity) {
        mProxyActivity = proxyActivity;
    }

    @Override
    public void setContentView(int layoutResID) {
       mProxyActivity.setContentView(layoutResID);
    }

    @Override
    public View findViewById(int id) {
        return mProxyActivity.findViewById(id);
    }

    @Override
    public Resources getResources() {
        return mProxyActivity.getResources();
    }

    @Override
    public Window getWindow() {
        return mProxyActivity.getWindow();
    }


    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        mProxyActivity.addContentView(view, params);
    }

    @Override
    public Intent getIntent() {
            return mProxyActivity.getIntent();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mProxyActivity.getClassLoader();
    }

//    @Override
//    public String getPackageName() {
//        return mPluginApk.packageName;
//    }


    @Override
    public LayoutInflater getLayoutInflater() {
        return mProxyActivity.getLayoutInflater();
    }

    @Override
    public MenuInflater getMenuInflater() {
        return mProxyActivity.getMenuInflater();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return mProxyActivity.getSharedPreferences(name, mode);
    }

    @Override
    public Context getApplicationContext() {
        return mProxyActivity.getApplicationContext();
    }

    @Override
    public WindowManager getWindowManager() {
        return mProxyActivity.getWindowManager();
    }
    @Override
    public Object getSystemService(String name) {

        return mProxyActivity.getSystemService(name);

    }
    @Override
    public void finish() {
        mProxyActivity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }
}
