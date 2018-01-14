package com.iceteaviet.englishnow.data.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 11/01/2018.
 * <p>
 * Model for getting OpenTok room information
 * <p>
 * Implements Serializable to mark that this object can stream into to a sequence of byte
 * and restore these objects from this stream of bytes
 */

public class OpenTokRoom implements Serializable {
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
