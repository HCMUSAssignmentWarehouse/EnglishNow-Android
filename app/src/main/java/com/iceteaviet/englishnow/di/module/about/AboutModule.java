package com.iceteaviet.englishnow.di.module.about;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.about.viewmodel.AboutViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@Module
public class AboutModule {
    @Provides
    AboutViewModel provideAboutViewModel(DataManager dataManager,
                                         SchedulerProvider schedulerProvider) {
        return new AboutViewModel(dataManager, schedulerProvider);
    }
}