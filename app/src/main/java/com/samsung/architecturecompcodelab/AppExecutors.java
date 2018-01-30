package com.samsung.architecturecompcodelab;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by sidia on 29/01/18.
 */

/**
 * Global executors for all application.
 *
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 *
 * */
public class AppExecutors {

    private static final int FIXED_THREAD_POOL = 3;

    private Executor mDiskIOExecutor;

    private Executor mMainThreadExecutor;

    private Executor mNetworkIOExecutor;

    private AppExecutors(Executor mDiskIOExecutor, Executor mMainThreadExecutor, Executor mNetworkIOExecutor) {
        this.mDiskIOExecutor = mDiskIOExecutor;
        this.mMainThreadExecutor = mMainThreadExecutor;
        this.mNetworkIOExecutor = mNetworkIOExecutor;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(FIXED_THREAD_POOL),
                new MainThreadExecutor());
    }

    public Executor getDiskIOExecutor() {
        return mDiskIOExecutor;
    }

    public Executor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    public Executor getNetworkIOExecutor() {
        return mNetworkIOExecutor;
    }

    /*
    * Internal static class does not contain reference for external class.
    * */
    private static class MainThreadExecutor implements Executor {

        private Handler mMainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mMainHandler.post(runnable);
        }
    }
}
