package com.iceteaviet.englishnow.data.remote.firebase;

import android.net.Uri;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.UploadTaskMessage;
import com.iceteaviet.englishnow.data.model.firebase.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseDataSource {
    boolean isLoggedIn();

    Single<AuthResult> doServerLoginFirebaseCall(LoginRequest.ServerLoginRequest request);

    Single<AuthResult> doServerRegisterFirebaseCall(RegisterRequest.ServerRegisterRequest request);

    Single<String> updateUserDisplayName(FirebaseUser user, String displayName);

    Observable<User> getCurrentUser();

    void doFirebaseLogoutCall();

    String getCurrentUserUid();

    String getCurrentUserDisplayName();

    String getCurrentUserEmail();

    String getCurrentUserPhotoUrl();

    void doPushUserToFirebase(String userUid, User user);

    Single<List<Status>> getAllStatusesOnce();

    Observable<List<Status>> getAllStatuses();

    void pushStatusToFirebase(Status status);

    Single<String> getUserPhotoUrl(String userUid);

    Observable<UploadTaskMessage> uploadPhoto(Uri data);

    void deletePhoto(String fileName);
}
