package com.iceteaviet.englishnow.data;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.UploadTaskMessage;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseDataSource;

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
    private final FirebaseDataSource firebaseDataSource;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(Context context, FirebaseDataSource firebaseDataSource, PreferencesHelper preferencesHelper) {
        this.context = context;
        this.firebaseDataSource = firebaseDataSource;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public boolean isLoggedIn() {
        return firebaseDataSource.isLoggedIn();
    }

    @Override
    public Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request) {
        return firebaseDataSource.doServerLoginFirebaseCall(request);
    }

    @Override
    public Single<AuthResult> doServerRegisterFirebaseCall(RegisterRequest.ServerRegisterRequest request) {
        return firebaseDataSource.doServerRegisterFirebaseCall(request);
    }

    @Override
    public Single<String> updateUserDisplayName(FirebaseUser user, String displayName) {
        return firebaseDataSource.updateUserDisplayName(user, displayName);
    }

    @Override
    public Observable<User> getCurrentUser() {
        return firebaseDataSource.getCurrentUser();
    }

    @Override
    public void doFirebaseLogoutCall() {
        firebaseDataSource.doFirebaseLogoutCall();
    }

    @Override
    public String getCurrentUserDisplayName() {
        return firebaseDataSource.getCurrentUserDisplayName();
    }

    @Override
    public String getCurrentUserUid() {
        return firebaseDataSource.getCurrentUserUid();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseDataSource.getCurrentUserEmail();
    }

    @Override
    public String getCurrentUserPhotoUrl() {
        return firebaseDataSource.getCurrentUserPhotoUrl();
    }

    @Override
    public void doPushUserToFirebase(String userUid, User user) {
        firebaseDataSource.doPushUserToFirebase(userUid, user);
    }

    @Override
    public void setUserAsLoggedOut() {
        //TODO: Implement me
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
    public Observable<List<Status>> getAllStatuses() {
        return firebaseDataSource.getAllStatuses();
    }

    @Override
    public Single<List<Status>> getAllStatusesOnce() {
        return firebaseDataSource.getAllStatusesOnce();
    }

    @Override
    public Single<String> getUserPhotoUrl(String userUid) {
        return firebaseDataSource.getUserPhotoUrl(userUid);
    }

    @Override
    public Observable<UploadTaskMessage> uploadPhoto(Uri data) {
        return firebaseDataSource.uploadPhoto(data);
    }

    @Override
    public void deletePhoto(String fileName) {
        firebaseDataSource.deletePhoto(fileName);
    }

    @Override
    public Observable<List<StatusItemData>> getAllStatusItems() {
        return firebaseDataSource.getAllStatusesOnce()
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
                        return Observable.zip(firebaseDataSource.getUserPhotoUrl(status.getOwnerUid()).toObservable(), Observable.just(status),
                                new BiFunction<String, Status, StatusItemData>() {
                                    @Override
                                    public StatusItemData apply(String avatarUrl, Status status) throws Exception {
                                        return new StatusItemData(status, avatarUrl);
                                    }
                                });
                    }
                })
                .toList()
                .toObservable();
    }

    @Override
    public void pushStatusToFirebase(Status status) {
        firebaseDataSource.pushStatusToFirebase(status);
    }
}
