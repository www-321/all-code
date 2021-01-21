package com.example.estemplate.treadPool;

import java.util.concurrent.*;

public class ThreadPoolDemo {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                4,
                20,
                TimeUnit.HOURS, new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

    }
}
