package com.iceteaviet.englishnow.di.module.splash;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.splash.viewmodel.SplashViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 27/12/2017.
 */

@Module
public class SplashModule {
    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SplashViewModel(dataManager, schedulerProvider);
    }
}
