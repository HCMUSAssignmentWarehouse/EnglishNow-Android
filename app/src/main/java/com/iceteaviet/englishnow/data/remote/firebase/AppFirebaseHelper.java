package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class AppFirebaseHelper implements FirebaseHelper {
    private static final String TAG = AppFirebaseHelper.class.getSimpleName();

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Inject
    public AppFirebaseHelper(FirebaseAuth auth, FirebaseDatabase database) {
        this.mAuth = auth;
        this.database = database;
    }

    @Override
    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public Single<AuthResult> loginFirebaseWithEmail(LoginRequest.ServerLoginRequest request) {
        return Single.create(emitter -> mAuth.signInWithEmailAndPassword(request.getEmail(), request.getPassword())
                .addOnSuccessListener(authResult -> emitter.onSuccess(authResult))
                .addOnFailureListener(e -> emitter.onError(e)));
    }

    @Override
    public Single<AuthResult> registerFirebaseWithEmail(RegisterRequest.ServerRegisterRequest request) {
        return Single.create(emitter -> mAuth.createUserWithEmailAndPassword(request.getEmail(), request.getPassword())
                .addOnSuccessListener(authResult -> {
                    //update display name
                    updateUserDisplayName(authResult.getUser(), request.getUsername()).subscribe();
                    emitter.onSuccess(authResult);
                })
                .addOnFailureListener(e -> emitter.onError(e)));
    }

    @Override
    public Single<String> updateUserDisplayName(FirebaseUser user, String displayName) {
        return Single.create(emitter -> user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(displayName)
                .build())
                .addOnSuccessListener(aVoid -> emitter.onSuccess(user.getDisplayName()))
                .addOnFailureListener(e -> emitter.onError(e)));
    }

    @Override
    public void logoutFirebase() {
        mAuth.signOut();
    }

    @Override
    public String getCurrentUserUid() {
        try {
            return mAuth.getCurrentUser().getUid();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String getCurrentUserDisplayName() {
        try {
            return mAuth.getCurrentUser().getDisplayName();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public String getCurrentUserEmail() {
        try {
            return mAuth.getCurrentUser().getEmail();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public String getCurrentUserPhotoUrl() {
        try {
            return mAuth.getCurrentUser().getPhotoUrl().getPath();
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
