package com.iceteaviet.englishnow.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public final class CommonUtils {
    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static AlertDialog showAlertDialog(Context context, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setMessage(message)
                .create();

        dialog.show();

        return dialog;
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }
}
