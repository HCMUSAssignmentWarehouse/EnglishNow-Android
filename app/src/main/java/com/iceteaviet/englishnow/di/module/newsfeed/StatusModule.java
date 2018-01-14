package com.iceteaviet.englishnow.di.module.newsfeed;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.di.NewsFeedViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.base.ViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.newsfeed.status.StatusAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.status.view.StatusFragment;
import com.iceteaviet.englishnow.ui.newsfeed.status.viewmodel.StatusViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 14/01/2018.
 */

@Module
public class StatusModule {
    @Provides
    StatusViewModel statusViewModel(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        return new StatusViewModel(dataManager, schedulerProvider);
    }

    @Provides
    StatusAdapter provideStatusAdapter(Context context) {
        return new StatusAdapter(context);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(StatusFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    @NewsFeedViewModelProviderFactory
    ViewModelProvider.Factory provideStatusViewModelProviderFactory(StatusViewModel statusViewModel) {
        return new ViewModelProviderFactory<>(statusViewModel);
    }
}