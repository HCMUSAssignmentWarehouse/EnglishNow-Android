package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class AppFirebaseHelper implements FirebaseHelper {
    private static final String TAG = AppFirebaseHelper.class.getSimpleName();
    private static final String AVAILABLE_LEARNERS = "available_learners";
    private static final String CHILD_MATCHED = "matched";
    private static final String CHILD_SUBSCRIBER_UID = "subscriberUid";

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

    @Override
    public Single<String> doConversationMatching(String userUid) {
        return Single.create(e -> database.getReference(AVAILABLE_LEARNERS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Boolean matched = child.child(CHILD_MATCHED) == null ? false : child.child(CHILD_MATCHED).getValue(Boolean.class);
                        if (!matched) {
                            String opponentUid = child.getKey();
                            subscribeToAvailableSpeaker(userUid, opponentUid);
                            e.onSuccess(opponentUid);
                            return;
                        }
                    }

                    //If every child not is already matched -> publish a new one
                    publishConversationMatchingRequest(userUid, e);
                } else {
                    publishConversationMatchingRequest(userUid, e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        }));
    }

    private void publishConversationMatchingRequest(String userUid, SingleEmitter<String> e) {
        Map<String, Object> data = new HashMap<>();
        data.put(CHILD_MATCHED, false);
        data.put(CHILD_SUBSCRIBER_UID, "");
        database.getReference(AVAILABLE_LEARNERS).child(userUid).setValue(data);

        database.getReference(AVAILABLE_LEARNERS).child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean matched = dataSnapshot.child(CHILD_MATCHED).getValue(Boolean.class);
                String subscriberUid = dataSnapshot.child(CHILD_SUBSCRIBER_UID).getValue(String.class);
                if (matched != null && matched && subscriberUid != null && !subscriberUid.isEmpty()) {
                    String subUid = dataSnapshot.child(CHILD_SUBSCRIBER_UID).getValue(String.class);
                    e.onSuccess(subUid);
                    database.getReference(AVAILABLE_LEARNERS).child(userUid).removeEventListener(this);
                } else {
                    //Do nothing
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    private void subscribeToAvailableSpeaker(String uid, String opponentUid) {
        database.getReference(AVAILABLE_LEARNERS).child(opponentUid).child(CHILD_SUBSCRIBER_UID).setValue(uid);
        database.getReference(AVAILABLE_LEARNERS).child(opponentUid).child(CHILD_MATCHED).setValue(true);
    }

    @Override
    public void removeLearnerFromAvailableList(String uid) {
        database.getReference(AVAILABLE_LEARNERS).child(uid).removeValue();
    }
}
