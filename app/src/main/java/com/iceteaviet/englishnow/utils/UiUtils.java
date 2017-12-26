package com.iceteaviet.englishnow.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public final class UiUtils {
    private UiUtils() {

    }

    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (makeTranslucent) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
