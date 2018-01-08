package com.iceteaviet.englishnow.di.module.main;

import android.arch.lifecycle.ViewModelProvider;

import com.iceteaviet.englishnow.ViewModelProviderFactory;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.main.viewmodel.MainViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 29/12/2017.
 */

@Module
public class MainModule {
    @Provides
    MainViewModel provideMainViewModel(DataManager dataSource,
                                       SchedulerProvider schedulerProvider) {
        return new MainViewModel(dataSource, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
        return new ViewModelProviderFactory<>(mainViewModel);
    }
}
