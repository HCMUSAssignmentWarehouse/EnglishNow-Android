package com.iceteaviet.englishnow.utils;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public final class BindingUtils {
    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"isVisible"})
    public static void setIsVisible(View view, boolean isVisible) {
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
