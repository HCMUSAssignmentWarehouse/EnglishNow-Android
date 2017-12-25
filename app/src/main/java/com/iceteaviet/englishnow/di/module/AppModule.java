package com.iceteaviet.englishnow.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/24/2017.
 */

//Because we wish to setup caching, we need an Application context
// AppModule will be used to provide this reference
//
@Module
public class AppModule {
    private static final String PREFS_NAME = "english_now_android";
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    //define a method annotated with @Provides that informs Dagger
    // that this method is in charge of providing the instance of the Application class
    @Provides
    @Singleton
    public Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences() {
        return application.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
    }
}
