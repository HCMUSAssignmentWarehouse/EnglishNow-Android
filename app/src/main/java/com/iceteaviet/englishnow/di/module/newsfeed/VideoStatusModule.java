package com.iceteaviet.englishnow.di.module.newsfeed;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.di.NewsFeedViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.base.ViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.VideoStatusAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.view.VideoStatusFragment;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.viewmodel.VideoStatusViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 14/01/2018.
 */

@Module
public class VideoStatusModule {
    @Provides
    VideoStatusViewModel videoStatusViewModel(DataManager dataManager,
                                              SchedulerProvider schedulerProvider) {
        return new VideoStatusViewModel(dataManager, schedulerProvider);
    }

    @Provides
    VideoStatusAdapter provideVideoStatusAdapter(Context context) {
        return new VideoStatusAdapter(context);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(VideoStatusFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    @NewsFeedViewModelProviderFactory
    ViewModelProvider.Factory provideVideoStatusViewModelProviderFactory(VideoStatusViewModel videoStatusViewModel) {
        return new ViewModelProviderFactory<>(videoStatusViewModel);
    }
}
