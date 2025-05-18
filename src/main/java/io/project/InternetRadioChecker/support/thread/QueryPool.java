package io.project.InternetRadioChecker.support.thread;

import java.util.concurrent.*;

public class QueryPool {

    public static final int MAX_THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 80;
    public static final int MAX_WORKED_QUERY_COUNT = MAX_THREAD_COUNT * 5;
    private final ThreadPoolExecutor threadPool;

    public QueryPool() {
        ThreadGroup defaultThreadGroup = new ThreadGroup("QueryPool");
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(MAX_WORKED_QUERY_COUNT);
        this.threadPool = new ThreadPoolExecutor(
                MAX_THREAD_COUNT,
                MAX_THREAD_COUNT, 0L,
                TimeUnit.MILLISECONDS,
                workQueue,
                new DefaultThreadFactory(defaultThreadGroup)
        );
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPool;
    }
}
