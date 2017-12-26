package com.iceteaviet.englishnow.data.remote;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseHelper {
    FirebaseAuth getFirebaseAuth();

    Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request);
}
