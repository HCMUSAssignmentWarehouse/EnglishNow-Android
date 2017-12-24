package com.iceteaviet.englishnow.di.component;

import com.iceteaviet.englishnow.EnglishNowApp;
import com.iceteaviet.englishnow.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Genius Doan on 12/24/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
//define what objects should be included as part of the dependency chain by modules
public interface AppComponent {
    void inject(EnglishNowApp app);
    // Note that the activities, services, or fragments that are allowed to request the dependencies declared by the modules
    // (by means of the @Inject annotation) should be declared in this class with individual inject() methods
    // void inject(MyFragment fragment);
    // void inject(MyService service);
}
