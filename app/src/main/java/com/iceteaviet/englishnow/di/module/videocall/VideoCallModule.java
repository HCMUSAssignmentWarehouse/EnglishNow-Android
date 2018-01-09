package com.iceteaviet.englishnow.di.module.videocall;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.videocall.viewmodel.VideoCallViewModel;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 09/01/2018.
 */

@Module
public class VideoCallModule {
    @Provides
    VideoCallViewModel provideVideoCallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new VideoCallViewModel(dataManager, schedulerProvider);
    }
}
