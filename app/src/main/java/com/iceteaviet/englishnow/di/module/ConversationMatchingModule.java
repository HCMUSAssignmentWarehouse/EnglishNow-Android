package com.iceteaviet.englishnow.di.module;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.matching.viewmodel.ConversationMatchingViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public class ConversationMatchingModule {
    @Provides
    ConversationMatchingViewModel provideLoginViewModel(DataManager dataManager,
                                                        SchedulerProvider schedulerProvider) {
        return new ConversationMatchingViewModel(dataManager, schedulerProvider);
    }
}