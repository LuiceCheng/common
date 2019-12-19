package com.example.common.utils.threadPool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/5/6 10:47
 */
public class ThreadPoolExecutorFactory {
    private static ThreadPoolExecutorFactory factory = null;
    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>(1000));

    public ThreadPoolExecutorFactory() {

    }

    public static synchronized ThreadPoolExecutorFactory getFactory() {
        if (null == factory) {
            factory = new ThreadPoolExecutorFactory();
        }
        return factory;
    }

    public ThreadPoolExecutor getExecutor() {
        return executor;
    }

    public void executor(Runnable runnable){
        executor.execute(runnable);
    }
}
