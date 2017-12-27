package com.iceteaviet.englishnow.data.remote;

import com.iceteaviet.englishnow.data.model.api.LoginRequest;
import com.iceteaviet.englishnow.data.model.api.LoginResponse;

import io.reactivex.Single;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface ApiManager {
    ApiHeader getApiHeader();

    //Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    //Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    //Single<LogoutResponse> doLogoutApiCall();

    //Single<BlogResponse> getBlogApiCall();

    //Single<OpenSourceResponse> getOpenSourceApiCall();
}
