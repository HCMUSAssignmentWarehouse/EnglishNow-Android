package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.iceteaviet.englishnow.data.model.firebase.base.NewsFeedItem;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by Genius Doan on 11/01/2018.
 * <p>
 * Model for storing information about a status
 * <p>
 * Implements Serializable to mark that this object can stream into to a sequence of byte
 * and restore these objects from this stream of bytes
 */

@IgnoreExtraProperties
public class Status extends NewsFeedItem implements Serializable {
    @PropertyName("content")
    protected String content;

    @PropertyName("photo")
    protected String photoUrl;

    public Status() {
        //Required for deserialize
    }

    public Status(String ownerUid, String ownerUsername, String content, String photoUrl, int likeCount, long timestamp, List<Comment> comments, Set<String> likes) {
        super(likeCount, timestamp, ownerUid, ownerUsername, comments, likes);
        this.content = content;
        this.photoUrl = photoUrl;
    }

    public Status(Builder builder) {
        super(builder);
        this.content = builder.content;
        this.photoUrl = builder.photoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @PropertyName("photo")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    //Builder design pattern
    public static class Builder extends NewsFeedItem.Builder<Builder> {
        protected String content;
        protected String photoUrl;

        public Builder() {

        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Status build() {
            return new Status(this);
        }
    }
}
