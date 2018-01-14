package com.iceteaviet.englishnow.data.remote.api;

import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface ApiHelper {
    Single<OpenTokRoom> getOpenTokRoomInfo(String roomName);
}
