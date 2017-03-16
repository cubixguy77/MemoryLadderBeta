package framework.executor.impl;

import android.os.Handler;

import javax.inject.Inject;

import framework.executor.MainThread;

/**
 * This class makes sure that the runnable we provide will be run on the main UI thread.
 * <p/>
 * Created by dmilicic on 7/29/15.
 */
public class MainThreadImpl implements MainThread {

    private Handler mHandler;

    @Inject
    public MainThreadImpl(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}