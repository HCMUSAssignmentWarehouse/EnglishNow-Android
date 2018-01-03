package com.iceteaviet.englishnow.ui.others.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.others.PostLoginNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class PostLoginViewModel extends BaseViewModel<PostLoginNavigator> {
    public PostLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onFindNowButtonClicked() {
        getNavigator().navigateToConversationMatchingScreen();
    }

    public void onSkipButtonClicked() {
        getNavigator().navigateToNewsFeedScreen();
    }
}
