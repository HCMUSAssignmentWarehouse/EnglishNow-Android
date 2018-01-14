package com.iceteaviet.englishnow.di.module.newsfeed;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedPagerAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.view.NewsFeedFragment;
import com.iceteaviet.englishnow.ui.newsfeed.viewmodel.NewsFeedViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public class NewsFeedModule {
    @Provides
    public NewsFeedViewModel provideNewsfeedViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new NewsFeedViewModel(dataManager, schedulerProvider);
    }

    @Provides
    public NewsFeedPagerAdapter provideNewsFeedPagerAdapter(NewsFeedFragment fragment) {
        return new NewsFeedPagerAdapter(fragment.getChildFragmentManager());
    }
}
