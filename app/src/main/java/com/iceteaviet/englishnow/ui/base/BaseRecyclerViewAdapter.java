package com.iceteaviet.englishnow.ui.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> mItems = new ArrayList<>();

    public void setItems(List<T> items) {
        if (this.mItems == items)
            return;

        mItems = items;
        notifyDataSetChanged();
    }

    protected int indexOf(T item) {
        return mItems.indexOf(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
