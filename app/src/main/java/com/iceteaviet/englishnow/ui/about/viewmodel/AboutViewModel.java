package com.iceteaviet.englishnow.ui.about.viewmodel;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.about.AboutHandler;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class AboutViewModel extends BaseViewModel<AboutHandler> {

    public AboutViewModel(AppDataSource appDataSource,
                          SchedulerProvider schedulerProvider) {
        super(appDataSource, schedulerProvider);
    }

    public void onNavBackClick() {
        getHandler().goBack();
    }
}
