package com.iceteaviet.englishnow.di.module.intro;

import com.iceteaviet.englishnow.data.DataManager;
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
    public IntroViewModel provideIntroViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new IntroViewModel(dataManager, schedulerProvider);
    }
}
