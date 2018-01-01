package com.iceteaviet.englishnow.ui.main.viewmodel;

import android.databinding.ObservableField;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.main.MainHandler;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 29/12/2017.
 */

public class MainViewModel extends BaseViewModel<MainHandler> {

    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();

    public MainViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }

    public void onNavigationViewCreated() {
        final String currentUserName = getAppDataSource().getCurrentUserDisplayName();
        if (currentUserName != null && !currentUserName.isEmpty()) {
            userName.set(currentUserName);
        }

        final String currentUserEmail = getAppDataSource().getCurrentUserEmail();
        if (currentUserEmail != null && !currentUserEmail.isEmpty()) {
            userEmail.set(currentUserEmail);
        }

        final String profilePicUrl = getAppDataSource().getCurrentUserPhotoUrl();
        if (profilePicUrl != null && !profilePicUrl.isEmpty()) {
            userProfilePicUrl.set(profilePicUrl);
        }
    }


    public void logout() {
        setIsLoading(true);
        getAppDataSource().doFirebaseLogoutCall();
        getAppDataSource().setUserAsLoggedOut();
        setIsLoading(false);
        getHandler().navigateToLoginScreen();
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
}
