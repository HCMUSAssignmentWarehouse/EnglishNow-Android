package com.iceteaviet.englishnow.data.remote.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iceteaviet.englishnow.data.model.firebase.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public class NewsFeedItemRepository implements NewsFeedItemDataSource {
    private static final String STATUS = "status";

    private FirebaseDatabase database;

    @Inject
    public NewsFeedItemRepository(FirebaseDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<List<Status>> fetchAll() {
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
    public Single<List<Status>> fetchAllOnce() {
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
    public void createOrUpdate(Status status) {
        database.getReference(STATUS).push().setValue(status);
    }
}
