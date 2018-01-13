package com.iceteaviet.englishnow.ui.newsfeed.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class StatusViewModel extends BaseViewModel<NewsFeedNavigator> {
    private final ObservableArrayList<StatusItemViewModel> statusItems = new ObservableArrayList<>(); //TODO: try to use list viewmodel
    private final MutableLiveData<List<StatusItemViewModel>> statusItemsLiveData;

    public StatusViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        statusItemsLiveData = new MutableLiveData<>();
        loadNewsFeedItems();
    }

    private void loadNewsFeedItems() {
        getCompositeDisposable().add(getDataManager()
                .getNewsFeedItemRepository()
                .fetchAll()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(statusList -> {
                    if (statusList != null) {
                        statusItemsLiveData.setValue(getViewModelList(statusList));
                    }
                }, throwable -> throwable.printStackTrace()));
    }

    public void deleteNewsFeedItem(int position) {
        statusItems.remove(position);
        statusItemsLiveData.getValue().remove(position);
    }

    public MutableLiveData<List<StatusItemViewModel>> getStatusItemsLiveData() {
        return statusItemsLiveData;
    }

    public ObservableArrayList<StatusItemViewModel> getStatusItems() {
        return statusItems;
    }

    public void setStatusItems(List<StatusItemViewModel> list) {
        statusItems.clear();
        statusItems.addAll(list);
    }

    //TODO: Only reload changed items
    private List<StatusItemViewModel> getViewModelList(List<Status> statusList) {
        ArrayList<StatusItemViewModel> statusItemViewModels = new ArrayList<>();
        for (int i = 0; i < statusList.size(); i++) {
            Status status = statusList.get(i);
            StatusItemViewModel itemViewModel = new StatusItemViewModel(status);
            getCompositeDisposable().add(getDataManager()
                    .getUserRepository()
                    .fetchOnce(status.getOwnerUid())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(s -> itemViewModel.setAvatarUrl(s.getProfilePic()), throwable -> throwable.printStackTrace())
            );
            statusItemViewModels.add(itemViewModel);
        }
        return statusItemViewModels;
    }
}
