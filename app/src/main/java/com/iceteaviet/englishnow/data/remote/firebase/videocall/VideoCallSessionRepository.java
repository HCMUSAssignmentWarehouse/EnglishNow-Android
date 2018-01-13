package com.iceteaviet.englishnow.data.remote.firebase.videocall;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iceteaviet.englishnow.data.model.firebase.VideoCallSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 11/01/2018.
 */

public class VideoCallSessionRepository implements VideoCallSessionDataSource {
    private static final String SESSIONS = "sessions";

    private FirebaseDatabase database;

    @Inject
    public VideoCallSessionRepository(FirebaseDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<List<VideoCallSession>> fetchAll() {
        return Observable.create(emitter -> database.getReference(SESSIONS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<VideoCallSession> sessions = new ArrayList<>();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            VideoCallSession session = itemSnapshot.getValue(VideoCallSession.class);
                            sessions.add(session);
                        }

                        emitter.onNext(sessions);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public void createOrUpdate(VideoCallSession session) {
        database.getReference(SESSIONS).child(session.getSessionId()).setValue(session);
    }

    @Override
    public Single<Boolean> isExist(String sessionId) {
        return Single.create(e -> database.getReference(SESSIONS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                    e.onSuccess(true);
                else
                    e.onSuccess(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                e.onError(databaseError.toException());
            }
        }));
    }

    @Override
    public Single<VideoCallSession> fetchOnce(String sessionId) {
        return Single.create(emitter -> database.getReference(SESSIONS).child(sessionId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        VideoCallSession session = dataSnapshot.getValue(VideoCallSession.class);
                        if (session != null) {
                            emitter.onSuccess(session);
                        } else {
                            emitter.onSuccess(new VideoCallSession());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public Observable<VideoCallSession> fetch(String sessionId) {
        return Observable.create(emitter -> database.getReference(SESSIONS).child(sessionId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        VideoCallSession session = dataSnapshot.getValue(VideoCallSession.class);
                        if (session != null) {
                            emitter.onNext(session);
                        } else {
                            emitter.onNext(new VideoCallSession());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                }));
    }

    @Override
    public void delete(String sessionId) {
        database.getReference(SESSIONS).child(sessionId).removeValue();
    }
}
