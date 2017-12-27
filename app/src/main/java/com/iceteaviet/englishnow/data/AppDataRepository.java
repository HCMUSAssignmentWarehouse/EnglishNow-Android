package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesManager;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.remote.FirebaseManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

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
}
