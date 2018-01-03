package com.iceteaviet.englishnow.ui.splash.viewmodel;

import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.splash.SplashNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void startDataLoading() {
        updateUI(getDataManager().getFirebaseAuth().getCurrentUser());
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            if (getDataManager().getAppLaunchFirstTime()) {
                getNavigator().navigateToIntroScreen();

                //Write new value
                getDataManager().setAppLaunchFirstTime(false);
            } else {
                getNavigator().navigateToLoginScreen();
            }
        } else {
            getNavigator().navigateToPostLoginScreen();
        }
    }
}
