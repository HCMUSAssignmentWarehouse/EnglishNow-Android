package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesManager;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;
import com.iceteaviet.englishnow.data.model.api.Status;
import com.iceteaviet.englishnow.data.model.api.User;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseManager;

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
public class AppDataRepository implements AppDataSource {
    private final Context context;
    private final FirebaseManager firebaseManager;
    private final PreferencesManager preferencesManager;

    @Inject
    public AppDataRepository(Context context, FirebaseManager firebaseManager, PreferencesManager preferencesManager) {
        this.context = context;
        this.firebaseManager = firebaseManager;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return firebaseManager.getFirebaseAuth();
    }

    @Override
    public Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request) {
        return firebaseManager.doServerLoginFirebaseCall(request);
    }

    @Override
    public Single<AuthResult> doServerRegisterFirebaseCall(RegisterRequest.ServerRegisterRequest request) {
        return firebaseManager.doServerRegisterFirebaseCall(request);
    }

    @Override
    public void doFirebaseLogoutCall() {
        firebaseManager.doFirebaseLogoutCall();
    }

    @Override
    public String getCurrentUserDisplayName() {
        return firebaseManager.getCurrentUserDisplayName();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebaseManager.getCurrentUserEmail();
    }

    @Override
    public String getCurrentUserPhotoUrl() {
        return firebaseManager.getCurrentUserPhotoUrl();
    }

    @Override
    public void doPushUserToFirebase(String userUid, User user) {
        firebaseManager.doPushUserToFirebase(userUid, user);
    }

    @Override
    public void setUserAsLoggedOut() {
        //TODO: Implement me
    }

    @Override
    public void putString(String key, String value) {
        preferencesManager.putString(key, value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return preferencesManager.getString(key, defaultValue);
    }

    @Override
    public void putBoolean(String key, Boolean value) {
        preferencesManager.putBoolean(key, value);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        return preferencesManager.getBoolean(key, defaultValue);
    }

    @Override
    public Boolean getAppLaunchFirstTime() {
        return preferencesManager.getAppLaunchFirstTime();
    }

    @Override
    public void setAppLaunchFirstTime(Boolean isFirstTime) {
        preferencesManager.setAppLaunchFirstTime(isFirstTime);
    }

    @Override
    public Single<List<Status>> getAllStatuses() {
        return firebaseManager.getAllStatuses();
    }

    @Override
    public Single<String> getUserPhotoUrl(String userUid) {
        return firebaseManager.getUserPhotoUrl(userUid);
    }

    @Override
    public Observable<List<StatusItemData>> getAllStatusItems() {
        return firebaseManager.getAllStatuses()
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
                        return Observable.zip(firebaseManager.getUserPhotoUrl(status.getOwnerUid()).toObservable(), Observable.just(status),
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
}
