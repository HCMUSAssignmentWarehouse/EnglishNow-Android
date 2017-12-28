package com.iceteaviet.englishnow.ui.others.viewmodel;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.others.PostLoginHandler;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public class PostLoginViewModel extends BaseViewModel<PostLoginHandler> {
    public PostLoginViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }

    public void onFindNowButtonClicked() {
        getHandler().navigateToConversationMatchingScreen();
    }

    public void onSkipButtonClicked() {
        getHandler().navigateToNewsFeedScreen();
    }
}
