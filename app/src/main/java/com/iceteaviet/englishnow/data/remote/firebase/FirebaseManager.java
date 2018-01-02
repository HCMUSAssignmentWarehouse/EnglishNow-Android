package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;
import com.iceteaviet.englishnow.data.model.api.Status;
import com.iceteaviet.englishnow.data.model.api.User;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseManager {
    FirebaseAuth getFirebaseAuth();

    Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request);

    Single<AuthResult> doServerRegisterFirebaseCall(RegisterRequest.ServerRegisterRequest request);

    void doFirebaseLogoutCall();

    String getCurrentUserDisplayName();

    String getCurrentUserEmail();

    String getCurrentUserPhotoUrl();

    void doPushUserToFirebase(String userUid, User user);

    Single<List<Status>> getAllStatuses();

    Single<String> getUserPhotoUrl(String userUid);
}
