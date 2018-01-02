package com.iceteaviet.englishnow.utils;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iceteaviet.englishnow.ui.base.BaseRecyclerViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

    @BindingAdapter({"src"})
    public static void setImageViewSrc(ImageView view, String src) {
        Glide.with(view.getContext())
                .load(src)
                .into(view);
    }

    @BindingAdapter({"src"})
    public static void setImageViewResource(ImageView imageView, @DrawableRes int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter({"android:src"})
    public static void setCircleImageViewResource(CircleImageView imageView, @DrawableRes int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, List<T> items) {
        BaseRecyclerViewAdapter<T> adapter = (BaseRecyclerViewAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) adapter.setItems(items);
    }
}
