package com.iceteaviet.englishnow.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iceteaviet.englishnow.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

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

    //TODO: Progress dialog deprecated, change to normal dialog
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
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

    public static String randomAlphanumericString(int count) {
        char[] chars = new char[count];
        Random random = new Random();
        final String POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < count; i++) {
            chars[i] = POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length()));
        }
        return new String(chars);
    }
}
