package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.main.viewmodel.ComposerViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 06/01/2018.
 */

@Module
public class ComposerModule {
    @Provides
    ComposerViewModel provideComposerViewModel(DataManager dataManager,
                                               SchedulerProvider schedulerProvider) {
        return new ComposerViewModel(dataManager, schedulerProvider);
    }
}
