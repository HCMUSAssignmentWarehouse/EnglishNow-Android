package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public class Comment implements Serializable {
    @PropertyName("content")
    private String content;

    @PropertyName("timestamp")
    private long timestamp;

    @PropertyName("userId")
    private String userId;

    @PropertyName("username")
    private String userName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("username")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
