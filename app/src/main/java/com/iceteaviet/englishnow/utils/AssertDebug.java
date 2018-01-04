package com.iceteaviet.englishnow.utils;

/**
 * Created by Genius Doan on 05/01/2018.
 */

import com.iceteaviet.englishnow.BuildConfig;

/**
 * Android Studio complains about built-in assertions so this is an alternative.
 */
public class AssertDebug {
    private static boolean ENABLED = BuildConfig.DEBUG;

    public static void expect(boolean expression) {
        if (ENABLED && !expression) {
            throw new AssertionError();
        }
    }
}