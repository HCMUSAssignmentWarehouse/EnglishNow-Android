package com.iceteaviet.englishnow.ui.about.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.about.AboutNavigator;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class AboutViewModel extends BaseViewModel<AboutNavigator> {

    public AboutViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackButtonClick() {
        getNavigator().navigateBack();
    }
}
