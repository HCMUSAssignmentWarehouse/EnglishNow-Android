package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.iceteaviet.englishnow.data.model.firebase.message.LoginMessage;
import com.iceteaviet.englishnow.data.model.firebase.message.RegisterMessage;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface FirebaseHelper {
    boolean isLoggedIn();

    Single<AuthResult> loginFirebaseWithEmail(LoginMessage.ServerLoginRequest request);

    Single<AuthResult> registerFirebaseWithEmail(RegisterMessage.ServerRequest request);

    Single<String> updateUserDisplayName(FirebaseUser user, String displayName);

    String getCurrentUserUid();

    String getCurrentUserDisplayName();

    String getCurrentUserEmail();

    String getCurrentUserPhotoUrl();

    void logoutFirebase();

    Single<String> doConversationMatching(String userUid);

    void removeLearnerFromAvailableList(String uid);
}
