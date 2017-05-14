package com.sven.dynamicload;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    PluginManager pluginMr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pluginMr.init(getApplicationContext());
        pluginMr = PluginManager.getInstance();
        String pluginApkPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
               File.separator+"plugins"+File.separator+"plugin.apk";
        String pluginClassz = "com.sven.dynamicload.sample.plugin.MainActivity";
        String pluginPackage = "com.sven.dynamicload.sample.plugin";
        pluginMr.loadApk(pluginApkPath);
        Intent intent = new Intent();
        intent.putExtra("PLUGIN_CLASS_NAME",pluginClassz);
        intent.putExtra("PACKAGE_NAME",pluginPackage);
        pluginMr.startActivity(intent);

    }
}
