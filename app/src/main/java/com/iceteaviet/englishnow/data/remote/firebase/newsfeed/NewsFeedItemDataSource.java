package com.iceteaviet.englishnow.data.remote.firebase.newsfeed;

import com.iceteaviet.englishnow.data.model.firebase.Status;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public interface NewsFeedItemDataSource {
    Observable<List<Status>> fetchAll();

    Single<List<Status>> fetchAllOnce();


    void createOrUpdate(Status status);
}
