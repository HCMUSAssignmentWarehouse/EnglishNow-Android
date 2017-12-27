package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.intro.viewmodel.IntroViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public class IntroModule {
    @Provides
    public IntroViewModel provideIntroViewModel(AppDataSource appDataSource, SchedulerProvider schedulerProvider) {
        return new IntroViewModel(appDataSource, schedulerProvider);
    }
}
