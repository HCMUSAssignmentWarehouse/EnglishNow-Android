package com.iceteaviet.englishnow.data.remote.firebase.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iceteaviet.englishnow.data.model.firebase.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 08/01/2018.
 * <p>
 * Repository to mediates between the view-model and Firebase data mapping layers
 * To create or fetch information about users from Firebase database
 */

@Singleton
public class UserRepository implements UserDataSource {
    private static final String USER_PROFILE = "user_profile";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;


    @Inject
    public UserRepository(FirebaseAuth auth, FirebaseDatabase database) {
        this.mAuth = auth;
        this.database = database;
    }

    @Override
    public Observable<User> fetch() {
        return Observable.create(emitter -> {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                emitter.onError(new Throwable("Cannot find current user!"));
                return;
            }
            String currUid = currentUser.getUid();
            database.getReference(USER_PROFILE).child(currUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    emitter.onNext(user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }

    @Override
    public Single<User> fetchOnce(String uid) {
        return Single.create(emitter -> {
            if (uid == null || uid.isEmpty()) {
                emitter.onError(new IllegalArgumentException("User uid must not be null!"));
                return;
            }

            database.getReference(USER_PROFILE).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        emitter.onSuccess(user);
                    } else
                        emitter.onError(new DatabaseException("Cannot find user!"));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    emitter.onError(databaseError.toException());
                }
            });
        });
    }

    @Override
    public void remove(String userUid) {
        database.getReference(USER_PROFILE).child(userUid).removeValue();
    }

    @Override
    public void createOrUpdate(String userUid, User user) {
        database.getReference(USER_PROFILE).child(userUid).setValue(user);
    }
}
