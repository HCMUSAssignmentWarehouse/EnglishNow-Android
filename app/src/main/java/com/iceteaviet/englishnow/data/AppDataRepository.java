package com.iceteaviet.englishnow.data;

import android.content.Context;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

    @Inject
    public AppDataRepository(Context context, FirebaseHelper firebaseHelper) {
        this.context = context;
        this.firebaseHelper = firebaseHelper;
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
}
