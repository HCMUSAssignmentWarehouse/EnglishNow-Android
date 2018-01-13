package com.iceteaviet.englishnow.ui.profile.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.profile.ProfileNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class ProfileViewModel extends BaseViewModel<ProfileNavigator> {

    private final ObservableField<Float> overallRating = new ObservableField<>();
    private final ObservableField<User> user = new ObservableField<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();


    public ProfileViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void startLoadingData() {
        getCompositeDisposable().add(getDataManager()
                .getUserRepository()
                .fetch()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(user -> {
                    if (user != null)
                        userLiveData.setValue(user);
                }, throwable -> throwable.printStackTrace())
        );
    }

    public ObservableField<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);

        //Calculate overall ratings
        overallRating.set(user.getOverallRating());
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public ObservableField<Float> getOverallRating() {
        return overallRating;
    }
}
