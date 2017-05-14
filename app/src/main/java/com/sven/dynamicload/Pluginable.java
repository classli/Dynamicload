package com.sven.dynamicload;

import android.app.Activity;


/**
 * Created by sven on 2016/1/7.
 */
public interface Pluginable {

    public void attach(Activity proxyActivity);

}
