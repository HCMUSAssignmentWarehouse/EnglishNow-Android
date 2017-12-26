package com.iceteaviet.englishnow.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}
