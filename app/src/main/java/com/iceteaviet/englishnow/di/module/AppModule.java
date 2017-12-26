package com.iceteaviet.englishnow.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.iceteaviet.englishnow.data.AppDataRepository;
import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.data.remote.AppFirebaseHelper;
import com.iceteaviet.englishnow.data.remote.FirebaseHelper;

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

    //define a method annotated with @Provides that informs Dagger
    // that this method is in charge of providing the instance of the Application class
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    public SharedPreferences providePreferences(SharedPreferences preferences) {
        return preferences;
    }

    @Provides
    @Singleton
    AppDataSource provideAppDataSource(AppDataRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    FirebaseHelper provideFirebaseHelper(AppFirebaseHelper firebaseHelper) {
        return firebaseHelper;
    }
}
