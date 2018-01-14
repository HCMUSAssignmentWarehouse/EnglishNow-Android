package com.iceteaviet.englishnow.data.remote.api;

import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;
import com.iceteaviet.englishnow.data.model.api.OpenTokRoomService;
import com.iceteaviet.englishnow.di.OpenTokApiClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by Genius Doan on 12/27/2017.
 * <p>
 * Entry point contains methods that help interacting with API calls
 */

@Singleton
public class AppApiHelper implements ApiHelper {
    private Retrofit openTokClient;

    @Inject
    public AppApiHelper(@OpenTokApiClient Retrofit openTokClient) {
        this.openTokClient = openTokClient;
    }

    @Override
    public Single<OpenTokRoom> getOpenTokRoomInfo(String roomName) {
        OpenTokRoomService service = openTokClient.create(OpenTokRoomService.class);
        return service.roomInfo(roomName);
    }
}
