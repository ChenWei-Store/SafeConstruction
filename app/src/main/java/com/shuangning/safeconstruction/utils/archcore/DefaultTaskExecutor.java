package com.shuangning.safeconstruction.utils.archcore;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Chenwei on 2023/9/18.
 */
public class DefaultTaskExecutor extends TaskExecutor {

    private final Object mLock = new Object();

    private final ExecutorService mDiskIO = Executors.newFixedThreadPool(4, new ThreadFactory() {
        private static final String THREAD_NAME_STEM = "arch_disk_io_";

        private final AtomicInteger mThreadId = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName(THREAD_NAME_STEM + mThreadId.getAndIncrement());
            return t;
        }
    });

    @Nullable
    private volatile Handler mMainHandler;

    @Override
    public void executeOnDiskIO(@NonNull Runnable runnable) {
        mDiskIO.execute(runnable);
    }

    @Override
    public void postToMainThread(@NonNull Runnable runnable) {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        mMainHandler.post(runnable);
    }

    @Override
    public void postToMainThreadDelay(@NonNull Runnable runnable, long delayMillis) {
        if (mMainHandler == null) {
            synchronized (mLock) {
                if (mMainHandler == null) {
                    mMainHandler = new Handler(Looper.getMainLooper());
                }
            }
        }
        mMainHandler.postDelayed(runnable, delayMillis);
    }

    @Override
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    @Override
    public void executeOnIDLEHandler(@NonNull Runnable runnable) {
        Looper.myQueue().addIdleHandler(() -> {
            runnable.run();
            return false;
        });
    }
}
