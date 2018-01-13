package com.iceteaviet.englishnow.data.remote.firebase;

import com.iceteaviet.englishnow.data.model.firebase.VideoCallSession;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Genius Doan on 11/01/2018.
 */

public interface VideoCallSessionDataSource {
    Observable<List<VideoCallSession>> fetchAll();

    void createOrUpdate(VideoCallSession status);

    Single<Boolean> isExist(String sessionId);

    Single<VideoCallSession> fetchOnce(String sessionId);

    Observable<VideoCallSession> fetch(String sessionId);

    void delete(String sessionId);
}
