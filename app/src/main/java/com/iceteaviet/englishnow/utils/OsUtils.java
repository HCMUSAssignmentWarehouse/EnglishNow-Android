package com.iceteaviet.englishnow.utils;

import android.os.Build;

import java.util.Set;

/**
 * Created by Genius Doan on 26/12/2017.
 */

public final class OsUtils {
    private static boolean sIsAtLeastICS_MR1;
    private static boolean sIsAtLeastJB;
    private static boolean sIsAtLeastJB_MR1;
    private static boolean sIsAtLeastJB_MR2;
    private static boolean sIsAtLeastKLP;
    private static boolean sIsAtLeastL;
    private static boolean sIsAtLeastL_MR1;
    private static boolean sIsAtLeastM;
    private static boolean sIsAtLeastN;

    static {
        final int v = getApiVersion();
        sIsAtLeastICS_MR1 = v >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
        sIsAtLeastJB = v >= Build.VERSION_CODES.JELLY_BEAN;
        sIsAtLeastJB_MR1 = v >= Build.VERSION_CODES.JELLY_BEAN_MR1;
        sIsAtLeastJB_MR2 = v >= Build.VERSION_CODES.JELLY_BEAN_MR2;
        sIsAtLeastKLP = v >= Build.VERSION_CODES.KITKAT;
        sIsAtLeastL = v >= Build.VERSION_CODES.LOLLIPOP;
        sIsAtLeastL_MR1 = v >= Build.VERSION_CODES.LOLLIPOP_MR1;
        sIsAtLeastM = v >= Build.VERSION_CODES.M;
        sIsAtLeastN = v >= Build.VERSION_CODES.N;
    }

    private OsUtils() {

    }

    /**
     * @return True if the version of Android that we're running on is at least Ice Cream Sandwich
     * MR1 (API level 15).
     */
    public static boolean isAtLeastICS_MR1() {
        return sIsAtLeastICS_MR1;
    }

    /**
     * @return True if the version of Android that we're running on is at least Jelly Bean
     * (API level 16).
     */
    public static boolean isAtLeastJB() {
        return sIsAtLeastJB;
    }

    /**
     * @return True if the version of Android that we're running on is at least Jelly Bean MR1
     * (API level 17).
     */
    public static boolean isAtLeastJB_MR1() {
        return sIsAtLeastJB_MR1;
    }

    /**
     * @return True if the version of Android that we're running on is at least Jelly Bean MR2
     * (API level 18).
     */
    public static boolean isAtLeastJB_MR2() {
        return sIsAtLeastJB_MR2;
    }

    /**
     * @return True if the version of Android that we're running on is at least KLP
     * (API level 19).
     */
    public static boolean isAtLeastKLP() {
        return sIsAtLeastKLP;
    }

    /**
     * @return True if the version of Android that we're running on is at least L
     * (API level 21).
     */
    public static boolean isAtLeastL() {
        return sIsAtLeastL;
    }

    /**
     * @return True if the version of Android that we're running on is at least L MR1
     * (API level 22).
     */
    public static boolean isAtLeastL_MR1() {
        return sIsAtLeastL_MR1;
    }

    /**
     * @return True if the version of Android that we're running on is at least M
     * (API level 23).
     */
    public static boolean isAtLeastM() {
        return sIsAtLeastM;
    }

    /**
     * @return True if the version of Android that we're running on is at least M
     * (API level 24).
     */
    public static boolean isAtLeastN() {
        return sIsAtLeastN;
    }

    /**
     * @return The Android API version of the OS that we're currently running on.
     */
    public static int getApiVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Creates a joined string from a Set<String> using the given delimiter.
     *
     * @param values
     * @param delimiter
     * @return
     */
    public static String joinFromSetWithDelimiter(
            final Set<String> values, final String delimiter) {
        if (values != null) {
            final StringBuilder joinedStringBuilder = new StringBuilder();
            boolean firstValue = true;
            for (final String value : values) {
                if (firstValue) {
                    firstValue = false;
                } else {
                    joinedStringBuilder.append(delimiter);
                }
                joinedStringBuilder.append(value);
            }
            return joinedStringBuilder.toString();
        }
        return null;
    }
}
