package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

/**
 * Created by Genius Doan on 11/01/2018.
 * <p>
 * Model for storing information about a comment of a status
 * <p>
 * Implements Serializable to mark that this object can stream into to a sequence of byte
 * and restore these objects from this stream of bytes
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
