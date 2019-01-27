package ca.toropov.microcad.fogPrettifier.util;

/**
 * Author: toropov
 * Date: 9/28/2018
 */
public class ThreadUtil {
    public static void onNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
