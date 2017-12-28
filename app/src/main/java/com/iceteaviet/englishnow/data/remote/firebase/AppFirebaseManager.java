package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;
import com.iceteaviet.englishnow.data.model.api.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class AppFirebaseManager implements FirebaseManager {

    private static final String USER_PROFILE = "user_profile";
    private FirebaseAuth mAuth; //Should we inject this?
    private FirebaseDatabase database;

    @Inject
    public AppFirebaseManager(FirebaseAuth auth, FirebaseDatabase database) {
        this.mAuth = auth;
        this.database = database;
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
                    emitter.onSuccess(authResult);
                })
                .addOnFailureListener(e -> emitter.onError(e)));
    }

    @Override
    public void doPushUserToFirebase(String userUid, User user) {
        database.getReference(USER_PROFILE).child(userUid).setValue(user);
    }
}
