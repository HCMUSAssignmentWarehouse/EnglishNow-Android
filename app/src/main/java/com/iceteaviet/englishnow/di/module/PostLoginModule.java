package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.others.viewmodel.PostLoginViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@Module
public class PostLoginModule {
    @Provides
    PostLoginViewModel providePostLoginViewModel(AppDataSource dataManager,
                                                 SchedulerProvider schedulerProvider) {
        return new PostLoginViewModel(dataManager, schedulerProvider);
    }
}
