package com.iceteaviet.englishnow.data.remote.api;

import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.LoginResponse;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class AppApiManager implements ApiManager {
    @Override
    public ApiHeader getApiHeader() {
        return null;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return null;
    }
}
