package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.DataManager;
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
    ProfileViewModel provideAboutViewModel(DataManager dataManager,
                                           SchedulerProvider schedulerProvider) {
        return new ProfileViewModel(dataManager, schedulerProvider);
    }

}