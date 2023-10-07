package com.shuangning.safeconstruction.utils.archcore;

import androidx.annotation.NonNull;

/**
 * Created by Chenwei on 2023/9/18.
 */
public abstract class TaskExecutor {
    /**
     * Executes the given task in the disk IO thread pool.
     *
     * @param runnable The runnable to run in the disk IO thread pool.
     */
    public abstract void executeOnDiskIO(@NonNull Runnable runnable);

    /**
     * Posts the given task to the main thread.
     *
     * @param runnable The runnable to run on the main thread.
     */
    public abstract void postToMainThread(@NonNull Runnable runnable);
    public abstract void postToMainThreadDelay(@NonNull Runnable runnable, long delayMillis);

    /**
     * Executes the given task on the main thread.
     * <p>
     * If the current thread is a main thread, immediately runs the given runnable.
     *
     * @param runnable The runnable to run on the main thread.
     */
    public void executeOnMainThread(@NonNull Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            postToMainThread(runnable);
        }
    }

    /**
     * Returns true if the current thread is the main thread, false otherwise.
     *
     * @return true if we are on the main thread, false otherwise.
     */
    public abstract boolean isMainThread();

    public abstract void executeOnIDLEHandler(@NonNull Runnable runnable);
}
