package com.example.employeemanagmentappjavafx.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadHelper {

    public static Executor createExecutor() {
        return Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

}
