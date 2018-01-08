package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.MediaDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.NewsFeedItemDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.UserDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by Genius Doan on 23/12/2017.
 */

@Singleton
public class AppDataManager implements DataManager {
    private final Context context;
    private final FirebaseHelper firebaseHelper;
    private final PreferencesHelper preferencesHelper;
    private final UserDataSource userRepository;
    private final NewsFeedItemDataSource newsFeedItemRepository;
    private final MediaDataSource mediaRepository;

    @Inject
    public AppDataManager(Context context, FirebaseHelper firebaseHelper, PreferencesHelper preferencesHelper,
                          UserDataSource userRepository, NewsFeedItemDataSource newsFeedItemRepository, MediaDataSource mediaRepository) {
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        this.preferencesHelper = preferencesHelper;
        this.userRepository = userRepository;
        this.newsFeedItemRepository = newsFeedItemRepository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public NewsFeedItemDataSource getNewsFeedItemRepository() {
        return newsFeedItemRepository;
    }

    @Override
    public UserDataSource getUserRepository() {
        return userRepository;
    }

    @Override
    public MediaDataSource getMediaRepository() {
        return mediaRepository;
    }

    @Override
    public boolean isLoggedIn() {
        return firebaseHelper.isLoggedIn();
    }

    @Override
    public Single<AuthResult> loginFirebaseWithEmail(LoginRequest.ServerLoginRequest request) {
        return firebaseHelper.loginFirebaseWithEmail(request);
    }

    @Override
    public Single<AuthResult> registerFirebaseWithEmail(RegisterRequest.ServerRegisterRequest request) {
        return firebaseHelper.registerFirebaseWithEmail(request);
    }

    @Override
    public Single<String> updateUserDisplayName(FirebaseUser user, String displayName) {
        return firebaseHelper.updateUserDisplayName(user, displayName);
    }

    @Override
    public void logoutFirebase() {
        firebaseHelper.logoutFirebase();
    }

    @Override
    public String getCurrentUserDisplayName() {
        return firebaseHelper.getCurrentUserDisplayName();
    }

    @Override
    public String getCurrentUserUid() {
        return firebaseHelper.getCurrentUserUid();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseHelper.getCurrentUserEmail();
    }

    @Override
    public String getCurrentUserPhotoUrl() {
        return firebaseHelper.getCurrentUserPhotoUrl();
    }


    @Override
    public void putString(String key, String value) {
        preferencesHelper.putString(key, value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return preferencesHelper.getString(key, defaultValue);
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        preferencesHelper.putBoolean(key, value);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return preferencesHelper.getBoolean(key, defaultValue);
    }

    @Override
    public Boolean getAppLaunchFirstTime() {
        return preferencesHelper.getAppLaunchFirstTime();
    }

    @Override
    public void setAppLaunchFirstTime(Boolean isFirstTime) {
        preferencesHelper.setAppLaunchFirstTime(isFirstTime);
    }

    @Override
    public Observable<List<StatusItemData>> fetchAllStatusItemData() {
        return newsFeedItemRepository.fetchAllOnce()
                .toObservable()
                .flatMap(new Function<List<Status>, ObservableSource<Status>>() {
                    @Override
                    public ObservableSource<Status> apply(List<Status> statuses) throws Exception {
                        return Observable.fromIterable(statuses);
                    }
                })
                .flatMap(new Function<Status, ObservableSource<StatusItemData>>() {
                    @Override
                    public ObservableSource<StatusItemData> apply(Status status) throws Exception {
                        return Observable.zip(userRepository.fetchOnce(status.getOwnerUid()).toObservable(), Observable.just(status),
                                new BiFunction<User, Status, StatusItemData>() {
                                    @Override
                                    public StatusItemData apply(User user, Status status) throws Exception {
                                        return new StatusItemData(status, user.getProfilePic());
                                    }
                                });
                    }
                })
                .toList()
                .toObservable();
    }
}
