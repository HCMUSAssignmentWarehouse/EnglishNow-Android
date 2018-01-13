package com.iceteaviet.englishnow.ui.intro.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.intro.IntroNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class IntroViewModel extends BaseViewModel<IntroNavigator> {
    public IntroViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNextButtonClicked() {
        getNavigator().navigateToNextScreen();
    }

    public void onSkipButtonClicked() {
        getNavigator().skip();
    }
}
