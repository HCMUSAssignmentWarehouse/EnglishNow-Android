package com.iceteaviet.englishnow.di.module;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.iceteaviet.englishnow.data.AppDataManager;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.local.prefs.AppPreferencesHelper;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.remote.api.ApiHelper;
import com.iceteaviet.englishnow.data.remote.api.AppApiHelper;
import com.iceteaviet.englishnow.data.remote.firebase.AppFirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.media.MediaDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.media.MediaRepository;
import com.iceteaviet.englishnow.data.remote.firebase.newsfeed.NewsFeedItemDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.newsfeed.NewsFeedItemRepository;
import com.iceteaviet.englishnow.data.remote.firebase.user.UserDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.user.UserRepository;
import com.iceteaviet.englishnow.data.remote.firebase.videocall.VideoCallSessionDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.videocall.VideoCallSessionRepository;
import com.iceteaviet.englishnow.utils.rx.AppSchedulerProvider;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/24/2017.
 * <p>
 * You need to define what objects should be included as part of the dependency chain by creating a Dagger 2 module.
 * This AppModule is used to provide dependencies about general object that needed throughout the app.
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
    FirebaseHelper provideFirebaseHelper(AppFirebaseHelper firebaseHelper) {
        return firebaseHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiDataSource(AppApiHelper repository) {
        return repository;
    }

    @Provides
    @Singleton
    UserDataSource provideUserDataSource(UserRepository userRepository) {
        return userRepository;
    }

    @Provides
    @Singleton
    NewsFeedItemDataSource provideNewsFeedItemDataSource(NewsFeedItemRepository newsFeedItemRepository) {
        return newsFeedItemRepository;
    }

    @Provides
    @Singleton
    MediaDataSource provideMediaDataSource(MediaRepository mediaRepository) {
        return mediaRepository;
    }

    @Provides
    @Singleton
    VideoCallSessionDataSource provideVideoCallSessionDataSource(VideoCallSessionRepository repository) {
        return repository;
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
    @Singleton
    FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }
}
