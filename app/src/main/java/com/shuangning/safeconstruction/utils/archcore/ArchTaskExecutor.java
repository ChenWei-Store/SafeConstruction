package com.shuangning.safeconstruction.utils.archcore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shuangning.safeconstruction.R;

import java.util.concurrent.Executor;

/**
 * Created by Chenwei on 2023/9/18.
 * 该类来源于androidx.arch.core:core-runtime，
 * 封装的比较好，但不能以依赖的方式用，copy出来自用
 */
public class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance;

    @NonNull
    private TaskExecutor mDelegate;

    @NonNull
    private final TaskExecutor mDefaultTaskExecutor;

    @NonNull
    public static final Executor sMainThreadExecutor =
            command -> getInstance().postToMainThread(command);

    @NonNull
    public static final Executor sIOThreadExecutor =
            command -> getInstance().executeOnDiskIO(command);

    private ArchTaskExecutor() {
        mDefaultTaskExecutor = new DefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }

    /**
     * Returns an instance of the task executor.
     *
     * @return The singleton ArchTaskExecutor.
     */
    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
        }
        return sInstance;
    }

    /**
     * Sets a delegate to handle task execution requests.
     * <p>
     * If you have a common executor, you can set it as the delegate and App Toolkit components will
     * use your executors. You may also want to use this for your tests.
     * <p>
     * Calling this method with {@code null} sets it to the default TaskExecutor.
     *
     * @param taskExecutor The task executor to handle task requests.
     */
    public void setDelegate(@Nullable TaskExecutor taskExecutor) {
        mDelegate = taskExecutor == null ? mDefaultTaskExecutor : taskExecutor;
    }

    @Override
    public void executeOnDiskIO(@NonNull Runnable runnable) {
        mDelegate.executeOnDiskIO(runnable);
    }

    @Override
    public void postToMainThread(@NonNull Runnable runnable) {
        mDelegate.postToMainThread(runnable);
    }

    @Override
    public void postToMainThreadDelay(@NonNull Runnable runnable, long delayMillis) {
        mDelegate.postToMainThreadDelay(runnable, delayMillis);
    }

    @NonNull
    public static Executor getMainThreadExecutor() {
        return sMainThreadExecutor;
    }

    @NonNull
    public static Executor getIOThreadExecutor() {
        return sIOThreadExecutor;
    }

    @Override
    public boolean isMainThread() {
        return mDelegate.isMainThread();
    }

    @Override
    public void executeOnIDLEHandler(@NonNull Runnable runnable) {
        mDelegate.executeOnIDLEHandler(runnable);
    }
}
