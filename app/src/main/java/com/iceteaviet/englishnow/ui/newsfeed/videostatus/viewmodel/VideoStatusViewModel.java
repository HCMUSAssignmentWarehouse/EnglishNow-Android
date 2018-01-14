package com.iceteaviet.englishnow.ui.newsfeed.videostatus.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.VideoStatusNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Created by Genius Doan on 14/01/2018.
 */

public class VideoStatusViewModel extends BaseViewModel<VideoStatusNavigator> {
    private final ObservableArrayList<VideoStatusItemViewModel> statusItems = new ObservableArrayList<>();
    private final MutableLiveData<List<VideoStatusItemViewModel>> statusItemsLiveData;

    public VideoStatusViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        statusItemsLiveData = new MutableLiveData<>();
        loadNewsFeedItems();
    }

    private void loadNewsFeedItems() {
    }

    public void deleteNewsFeedItem(int position) {
        statusItems.remove(position);
        statusItemsLiveData.getValue().remove(position);
    }

    public MutableLiveData<List<VideoStatusItemViewModel>> getStatusItemsLiveData() {
        return statusItemsLiveData;
    }

    public ObservableArrayList<VideoStatusItemViewModel> getStatusItems() {
        return statusItems;
    }

    public void setStatusItems(List<VideoStatusItemViewModel> list) {
        statusItems.clear();
        statusItems.addAll(list);
    }
}