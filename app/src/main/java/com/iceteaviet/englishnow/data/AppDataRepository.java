package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.remote.FirebaseHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 23/12/2017.
 */

@Singleton
public class AppDataRepository implements AppDataSource {
    private final Context context;
    private final FirebaseHelper firebaseHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataRepository(Context context, FirebaseHelper firebaseHelper, PreferencesHelper preferencesHelper) {
        this.context = context;
        this.firebaseHelper = firebaseHelper;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return firebaseHelper.getFirebaseAuth();
    }

    @Override
    public Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request) {
        return firebaseHelper.doServerLoginFirebaseCall(request);
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
}
