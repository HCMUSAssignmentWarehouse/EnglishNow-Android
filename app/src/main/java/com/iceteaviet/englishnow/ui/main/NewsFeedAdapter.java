package com.iceteaviet.englishnow.ui.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.databinding.StatusItemBinding;
import com.iceteaviet.englishnow.ui.base.BaseRecyclerViewAdapter;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class NewsFeedAdapter extends BaseRecyclerViewAdapter<StatusItemData> {
    protected Context mContext;

    public NewsFeedAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StatusItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.status_item, parent, false);
        return new StatusItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindStatusItemView(holder, position);
    }

    private void bindStatusItemView(RecyclerView.ViewHolder holder, int position) {
        StatusItemViewHolder viewHolder = (StatusItemViewHolder) holder;
        viewHolder.bind((StatusItemData) mItems.get(position));
    }

    public class StatusItemViewHolder extends RecyclerView.ViewHolder {
        private StatusItemBinding statusItemBinding;

        public StatusItemViewHolder(StatusItemBinding binding) {
            super(binding.getRoot());
            statusItemBinding = binding;
        }

        public void bind(StatusItemData status) {
            statusItemBinding.setStatusItemData(status);
        }
    }
}
