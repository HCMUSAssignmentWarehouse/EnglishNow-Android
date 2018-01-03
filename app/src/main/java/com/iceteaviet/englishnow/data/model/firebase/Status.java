package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.PropertyName;
import com.iceteaviet.englishnow.data.model.base.NewsFeedItem;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class Status extends NewsFeedItem {
    @PropertyName("content")
    protected String content;

    @PropertyName("photo")
    protected String photoUrl;

    public Status() {
        //Required for deserialize
    }

    public Status(String ownerUid, String ownerUsername, String content, String photoUrl, int likeCount, long timestamp) {
        this.ownerUid = ownerUid;
        this.ownerUsername = ownerUsername;
        this.content = content;
        this.photoUrl = photoUrl;
        this.likeCount = likeCount;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
