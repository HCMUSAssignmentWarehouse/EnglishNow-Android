package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.api.ApiDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.MediaDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.NewsFeedItemDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.UserDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.VideoCallSessionDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 23/12/2017.
 */

@Singleton
public class AppDataManager implements DataManager {
    private final Context context;
    private final ApiDataSource apiDataSource;
    private final FirebaseHelper firebaseHelper;
    private final PreferencesHelper preferencesHelper;
    private final UserDataSource userRepository;
    private final NewsFeedItemDataSource newsFeedItemRepository;
    private final MediaDataSource mediaRepository;
    private final VideoCallSessionDataSource videoCallSessionRepository;

    @Inject
    public AppDataManager(Context context, FirebaseHelper firebaseHelper, PreferencesHelper preferencesHelper, ApiDataSource apiDataSource,
                          UserDataSource userRepository, NewsFeedItemDataSource newsFeedItemRepository, MediaDataSource mediaRepository,
                          VideoCallSessionDataSource videoCallSessionRepository) {
        this.context = context;
        this.apiDataSource = apiDataSource;
        this.firebaseHelper = firebaseHelper;
        this.preferencesHelper = preferencesHelper;
        this.userRepository = userRepository;
        this.newsFeedItemRepository = newsFeedItemRepository;
        this.mediaRepository = mediaRepository;
        this.videoCallSessionRepository = videoCallSessionRepository;
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
    public VideoCallSessionDataSource getVideoCallSessionRepository() {
        return videoCallSessionRepository;
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
    public Single<String> doConversationMatching(String userUid) {
        return firebaseHelper.doConversationMatching(userUid);
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
                .flatMap(statuses -> Observable.fromIterable(statuses))
                .flatMap(status -> Observable.zip(userRepository.fetchOnce(status.getOwnerUid()).toObservable(), Observable.just(status),
                        (user, status1) -> new StatusItemData(status1, user.getProfilePic())))
                .toList()
                .toObservable();
    }

    @Override
    public Single<OpenTokRoom> getOpenTokRoomInfo(String roomName) {
        return apiDataSource.getOpenTokRoomInfo(roomName);
    }
}
