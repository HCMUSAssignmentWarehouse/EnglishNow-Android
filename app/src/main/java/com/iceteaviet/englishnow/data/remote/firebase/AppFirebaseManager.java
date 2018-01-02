package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.RegisterRequest;
import com.iceteaviet.englishnow.data.model.api.Status;
import com.iceteaviet.englishnow.data.model.api.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class AppFirebaseManager implements FirebaseManager {

    private static final String USER_PROFILE = "user_profile";
    private static final String STATUS = "status";

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

    @Override
    public void doFirebaseLogoutCall() {
        mAuth.signOut();
    }

    @Override
    public String getCurrentUserDisplayName() {
        return mAuth.getCurrentUser().getDisplayName();
    }

    @Override
    public String getCurrentUserEmail() {
        return mAuth.getCurrentUser().getEmail();
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
    public Single<List<Status>> getAllStatuses() {
        return Single.create(new SingleOnSubscribe<List<Status>>() {
            @Override
            public void subscribe(SingleEmitter<List<Status>> emitter) throws Exception {
                database.getReference(STATUS).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Status> newsFeedItems = new ArrayList<>();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            Status status = itemSnapshot.getValue(Status.class);
                            newsFeedItems.add(status);
                        }

                        emitter.onSuccess(newsFeedItems);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                });
            }
        });
    }

    @Override
    public Single<String> getUserPhotoUrl(String userUid) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                if (userUid == null)
                    emitter.onError(new IllegalArgumentException("User uid must not be null!"));

                database.getReference(USER_PROFILE).child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getProfilePic() == null) {
                                emitter.onSuccess("");
                            } else {
                                emitter.onSuccess(user.getProfilePic());
                            }
                        } else
                            emitter.onSuccess("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                });
            }
        });
    }
}
