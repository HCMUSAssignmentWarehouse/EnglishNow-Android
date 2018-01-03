package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.auth.viewmodel.RegisterViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@Module
public class AuthDialogModule {
    @Provides
    RegisterViewModel provideRegisterViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new RegisterViewModel(dataManager, schedulerProvider);
    }
}