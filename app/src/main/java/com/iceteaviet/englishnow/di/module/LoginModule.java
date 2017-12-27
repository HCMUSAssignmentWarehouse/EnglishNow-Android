package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.login.viewmodel.LoginViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public class LoginModule {
    @Provides
    LoginViewModel provideLoginViewModel(AppDataSource repository,
                                         SchedulerProvider schedulerProvider) {
        return new LoginViewModel(repository, schedulerProvider);
    }
}