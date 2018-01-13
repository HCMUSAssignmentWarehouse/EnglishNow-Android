package com.iceteaviet.englishnow.data.remote.firebase.user;

import com.iceteaviet.englishnow.data.model.firebase.User;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public interface UserDataSource {
    Observable<User> fetch();

    Single<User> fetchOnce(String uid);

    void createOrUpdate(String userUid, User user);
}
