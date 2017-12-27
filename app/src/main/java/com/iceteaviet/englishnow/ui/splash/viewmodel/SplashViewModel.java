package com.iceteaviet.englishnow.ui.splash.viewmodel;

import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.splash.SplashHandler;
import com.iceteaviet.englishnow.utils.AppConstants;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public class SplashViewModel extends BaseViewModel<SplashHandler> {

    public SplashViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public void startDataLoading() {
        updateUI(auth.getCurrentUser());
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            if (getAppDataSource().getBoolean(AppConstants.KEY_APP_LAUNCH_FIRST_TIME, true)) {
                getHandler().navigateToIntroScreen();

                //Write new value
                getAppDataSource().putBoolean(AppConstants.KEY_APP_LAUNCH_FIRST_TIME, false);
            } else {
                getHandler().navigateToLoginScreen();
            }
        } else {
            getHandler().navigateToPostLoginScreen();
        }
    }
}