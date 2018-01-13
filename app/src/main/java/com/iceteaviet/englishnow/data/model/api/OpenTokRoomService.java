package com.iceteaviet.englishnow.data.model.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Genius Doan on 11/01/2018.
 */

public interface OpenTokRoomService {
    @GET("room/{name}")
    Single<OpenTokRoom> roomInfo(@Path("name") String roomName);
}
