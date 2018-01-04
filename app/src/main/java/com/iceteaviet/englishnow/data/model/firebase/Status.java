package com.iceteaviet.englishnow.data.model.firebase;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.iceteaviet.englishnow.data.model.base.NewsFeedItem;

import java.util.List;
import java.util.Set;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@IgnoreExtraProperties
public class Status extends NewsFeedItem {
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

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
