package edu.monash.bridgingculture.service.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
    public final static ExecutorService executorService = Executors.newFixedThreadPool(2);
}
