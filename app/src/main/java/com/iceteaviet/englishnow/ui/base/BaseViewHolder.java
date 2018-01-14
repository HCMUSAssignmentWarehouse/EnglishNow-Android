package com.iceteaviet.englishnow.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Genius Doan on 12/26/2017.
 * <p>
 * Base class for ViewHolder pattern
 * <p>
 * used for View caching - Holder (arbitrary) object holds child widgets of each row
 * and when row is out of View then findViewById() won't be called but View will be recycled
 * and widgets will be obtained from Holder
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}
