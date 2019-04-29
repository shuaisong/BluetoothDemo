package com.reeching.bluetoothdemo;

import android.util.Log;

/**
 * ******************************************
 * ******************************************
 */
public class LogUtil {
    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append("=").append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void i(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void i(int message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message + ""));
    }

    public static void d(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void d(Object[] message) {
        getMethodNames(new Throwable().getStackTrace());
        StringBuilder sb = new StringBuilder("[");

        for (Object o :
                message) {
            if (o instanceof Integer)
                sb.append(o.toString()).append(",");
        }
        Log.d(className, createLog(sb.append("]").toString()));
    }

    public static void d(int message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(String.valueOf(message)));
    }

    public static void e(String message) {
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


}
