package com.iceteaviet.englishnow.ui.newsfeed.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class NewsFeedViewModel extends BaseViewModel<NewsFeedNavigator> {
    private final ObservableArrayList<StatusItemData> statusItems = new ObservableArrayList<>(); //TODO: try to use list viewmodel

    private final MutableLiveData<List<StatusItemData>> statusItemsLiveData;

    public NewsFeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        statusItemsLiveData = new MutableLiveData<>();
        loadNewsFeedItems();
    }

    private void loadNewsFeedItems() {
        getCompositeDisposable().add(getDataManager()
                .getAllStatusItems()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<StatusItemData>>() {
                    @Override
                    public void accept(List<StatusItemData> statusItemData) throws Exception {
                        if (statusItemData != null) {
                            statusItemsLiveData.setValue(statusItemData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

    public void deleteNewsFeedItem(int position) {
        statusItems.remove(position);
        statusItemsLiveData.getValue().remove(position);
    }

    public void setNewsFeedItems(List<StatusItemData> list) {
        statusItems.clear();
        statusItems.addAll(list);
    }

    public ObservableArrayList<StatusItemData> getStatusItems() {
        return statusItems;
    }

    public MutableLiveData<List<StatusItemData>> getStatusItemsLiveData() {
        return statusItemsLiveData;
    }
}
