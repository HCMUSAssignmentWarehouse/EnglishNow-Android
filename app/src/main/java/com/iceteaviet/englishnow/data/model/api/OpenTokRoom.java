package com.iceteaviet.englishnow.data.model.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Genius Doan on 11/01/2018.
 */

public class OpenTokRoom {
    @SerializedName("apiKey")
    protected String apiKey;
    @SerializedName("sessionId")
    protected String sessionId;
    @SerializedName("token")
    protected String token;

    public OpenTokRoom() {

    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
