package com.iceteaviet.englishnow.ui.intro.viewmodel;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.intro.IntroHandler;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class IntroViewModel extends BaseViewModel<IntroHandler> {
    public IntroViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public void onNextButtonClicked() {
        getHandler().goToNextScreen();
    }

    public void onSkipButtonClicked() {
        getHandler().skip();
    }
}
