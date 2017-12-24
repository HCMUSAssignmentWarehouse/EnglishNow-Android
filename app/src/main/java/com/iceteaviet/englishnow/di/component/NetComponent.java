package com.iceteaviet.englishnow.di.component;

import com.iceteaviet.englishnow.EnglishNowApp;
import com.iceteaviet.englishnow.di.module.AppModule;
import com.iceteaviet.englishnow.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Genius Doan on 12/25/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(EnglishNowApp app);
}