package com.iceteaviet.englishnow.ui.main.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.main.MainNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 29/12/2017.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    private final ObservableField<String> appVersion = new ObservableField<>("1.0");
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>("");
    private final ObservableBoolean isShowNavUsername = new ObservableBoolean(false);
    private final ObservableBoolean isShowNavEmail = new ObservableBoolean(false);


    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }

    public void onNavigationViewCreated() {
        final String currentUserName = getDataManager().getCurrentUserDisplayName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            userName.set(currentUserName);
            isShowNavUsername.set(true);
        } else {
            isShowNavUsername.set(false);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            userEmail.set(currentUserEmail);
            isShowNavEmail.set(true);
        } else {
            isShowNavEmail.set(false);
        }

        final String profilePicUrl = getDataManager().getCurrentUserPhotoUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            userProfilePicUrl.set(profilePicUrl);
        }
    }


    public void logout() {
        setIsLoading(true);
        getDataManager().doFirebaseLogoutCall();
        getDataManager().setUserAsLoggedOut();
        setIsLoading(false);
        getNavigator().navigateToLoginScreen();
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

    public ObservableBoolean getIsShowNavEmail() {
        return isShowNavEmail;
    }

    public ObservableBoolean getIsShowNavUsername() {
        return isShowNavUsername;
    }
}
