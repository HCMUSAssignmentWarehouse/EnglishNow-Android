package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.User;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseDataSource {
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
