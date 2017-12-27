package com.iceteaviet.englishnow.di.module;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.AppDataRepository;
import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.data.local.prefs.AppPreferencesManager;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesManager;
import com.iceteaviet.englishnow.data.remote.AppFirebaseManager;
import com.iceteaviet.englishnow.data.remote.FirebaseManager;
import com.iceteaviet.englishnow.utils.rx.AppSchedulerProvider;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

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
    //define a method annotated with @Provides that informs Dagger
    // that this method is in charge of providing the instance of the Application class
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    PreferencesManager providePreferencesHelper(AppPreferencesManager appPreferencesManager) {
        return appPreferencesManager;
    }

    @Provides
    @Singleton
    AppDataSource provideAppDataSource(AppDataRepository repository) {
        return repository;
    }

    @Provides
    @Singleton
    FirebaseManager provideFirebaseHelper(AppFirebaseManager firebaseHelper) {
        return firebaseHelper;
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
