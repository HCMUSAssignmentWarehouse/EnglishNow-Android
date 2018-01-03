package com.iceteaviet.englishnow.data.model.base;

import com.google.firebase.database.PropertyName;

/**
 * Created by Genius Doan on 02/01/2018.
 */

//TODO: Support like and comments
public abstract class NewsFeedItem {
    @PropertyName("like_number")
    protected int likeCount = 0;

    @PropertyName("time")
    protected long timestamp;

    @PropertyName("user")
    protected String ownerUid;

    @PropertyName("username")
    protected String ownerUsername;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
