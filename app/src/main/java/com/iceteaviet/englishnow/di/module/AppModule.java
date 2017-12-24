package com.iceteaviet.englishnow.di.module;

import android.app.Application;

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
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    //define a method annotated with @Provides that informs Dagger
    // that this method is in charge of providing the instance of the Application class
    @Provides
    @Singleton
    public Application providesApplication() {
        return mApplication;
    }
}
