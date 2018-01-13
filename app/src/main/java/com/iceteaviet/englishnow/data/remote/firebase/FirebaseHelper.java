package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseHelper {
    //Authentication
    boolean isLoggedIn();

    Single<AuthResult> loginFirebaseWithEmail(LoginRequest.ServerLoginRequest request);

    Single<AuthResult> registerFirebaseWithEmail(RegisterRequest.ServerRegisterRequest request);

    Single<String> updateUserDisplayName(FirebaseUser user, String displayName);

    String getCurrentUserUid();

    String getCurrentUserDisplayName();

    String getCurrentUserEmail();

    String getCurrentUserPhotoUrl();

    void logoutFirebase();

    Single<String> doConversationMatching(String userUid);

    void removeLearnerFromAvailableList(String uid);
}
