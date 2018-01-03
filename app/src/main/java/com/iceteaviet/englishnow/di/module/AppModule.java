package com.iceteaviet.englishnow.di.module;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iceteaviet.englishnow.data.AppDataManager;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.local.prefs.AppPreferencesHelper;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseRepository;
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
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideAppDataSource(AppDataManager repository) {
        return repository;
    }

    @Provides
    @Singleton
    FirebaseDataSource provideFirebaseHelper(FirebaseRepository firebaseHelper) {
        return firebaseHelper;
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
