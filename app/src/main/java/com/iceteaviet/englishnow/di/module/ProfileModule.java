package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.profile.viewmodel.ProfileViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@Module
public class ProfileModule {

    @Provides
    ProfileViewModel provideAboutViewModel(AppDataSource appDataSource,
                                           SchedulerProvider schedulerProvider) {
        return new ProfileViewModel(appDataSource, schedulerProvider);
    }

}