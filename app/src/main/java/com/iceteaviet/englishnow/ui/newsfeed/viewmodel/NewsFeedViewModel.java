package com.iceteaviet.englishnow.ui.newsfeed.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class NewsFeedViewModel extends BaseViewModel<NewsFeedNavigator> {
    public NewsFeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }
}
