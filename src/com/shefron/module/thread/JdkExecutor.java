package com.shefron.module.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2014/11/23.
 */
public class JdkExecutor {
    private static ExecutorService executorService = null;

    public static void main(String[] args){
        executorService = Executors.newFixedThreadPool(3);

        executorService.shutdown();

    }
}
