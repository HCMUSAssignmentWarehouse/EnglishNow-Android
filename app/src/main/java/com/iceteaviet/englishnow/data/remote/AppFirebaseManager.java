package com.iceteaviet.englishnow.data.remote;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class AppFirebaseManager implements FirebaseManager {

    private FirebaseAuth mAuth; //Should we inject this?

    @Inject
    public AppFirebaseManager(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        return mAuth;
    }

    @Override
    public Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request) {
        return Single.create(emitter -> mAuth.signInWithEmailAndPassword(request.getEmail(), request.getPassword())
                .addOnSuccessListener(authResult -> emitter.onSuccess(authResult))
                .addOnFailureListener(e -> emitter.onError(e)));
    }

    @Override
    public Single<AuthResult> doServerRegisterFirebaseCall(RegisterRequest.ServerRegisterRequest request) {
        return Single.create(emitter -> mAuth.createUserWithEmailAndPassword(request.getEmail(), request.getPassword())
                .addOnSuccessListener(authResult -> {
                    //TODO: Write data to firebase
                    emitter.onSuccess(authResult);
                })
                .addOnFailureListener(e -> emitter.onError(e)));
    }
}
