package com.iceteaviet.englishnow.data.remote.firebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteaviet.englishnow.data.model.firebase.LoginRequest;
import com.iceteaviet.englishnow.data.model.firebase.RegisterRequest;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.UploadTaskMessage;
import com.iceteaviet.englishnow.data.model.firebase.User;
import com.iceteaviet.englishnow.utils.AppLogger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

@Singleton
public class FirebaseRepository implements FirebaseDataSource {

    private static final String TAG = FirebaseRepository.class.getSimpleName();
    private static final String USER_PROFILE = "user_profile";
    private static final String STATUS = "status";
    private static final String STATUS_IMAGES = "status_images";


    private FirebaseAuth mAuth; //Should we inject this?
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    @Inject
    public FirebaseRepository(FirebaseAuth auth, FirebaseDatabase database, FirebaseStorage storage) {
        this.mAuth = auth;
        this.database = database;
        this.storage = storage;
    }

    @Override
    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
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
    public void doPushUserToFirebase(String userUid, User user) {
        database.getReference(USER_PROFILE).child(userUid).setValue(user);
    }

    @Override
    public void doFirebaseLogoutCall() {
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
    public Observable<User> getCurrentUser() {
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
    public Single<List<Status>> getAllStatusesOnce() {
        return Single.create(emitter -> database.getReference(STATUS)
                .addValueEventListener(new ValueEventListener() {
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
                }));
    }

    @Override
    public Observable<List<Status>> getAllStatuses() {
        return Observable.create(emitter -> database.getReference(STATUS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Status> statuses = new ArrayList<>();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            Status status = itemSnapshot.getValue(Status.class);
                            statuses.add(status);
                        }

                        emitter.onNext(statuses);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public void pushStatusToFirebase(Status status) {
        database.getReference(STATUS).push().setValue(status);
    }

    @Override
    public Observable<UploadTaskMessage> uploadPhoto(Uri data) {
        return Observable.create(e -> {
            StorageReference photoRef = storage.getReference(STATUS_IMAGES).child(data.getLastPathSegment());
            UploadTask uploadTask = photoRef.putFile(data);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                if (progress < 100)
                    e.onNext(new UploadTaskMessage(progress));
            }).addOnFailureListener(exception -> {
                // Handle unsuccessful uploads
                e.onError(exception);
            }).addOnSuccessListener(taskSnapshot -> {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                e.onNext(new UploadTaskMessage(100, downloadUrl));
                e.onComplete();
            });
        });
    }

    @Override
    public void deletePhoto(String fileName) {
        storage.getReference(STATUS_IMAGES)
                .child(fileName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AppLogger.e(TAG, e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public Single<String> getUserPhotoUrl(String userUid) {
        return Single.create(emitter -> {
            if (userUid == null || userUid.isEmpty()) {
                emitter.onError(new IllegalArgumentException("User uid must not be null!"));
                return;
            }

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
        });
    }
}
