package com.iceteaviet.englishnow.data.model.firebase.base;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.iceteaviet.englishnow.data.model.firebase.Comment;

import java.util.List;
import java.util.Set;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@IgnoreExtraProperties
public abstract class NewsFeedItem {
    @PropertyName("like_number")
    protected int likeCount = 0;

    @PropertyName("time")
    protected long timestamp;

    @PropertyName("ownerUid")
    protected String ownerUid;

    @PropertyName("ownerUsername")
    protected String ownerUsername;

    @PropertyName("comments")
    protected List<Comment> comments;

    @PropertyName("likes")
    protected Set<String> likes; //Store user uid of who like this status

    public NewsFeedItem() {

    }

    protected NewsFeedItem(int likeCount, long timestamp, String ownerUid, String ownerUsername, List<Comment> comments, Set<String> likes) {
        this.ownerUid = ownerUid;
        this.ownerUsername = ownerUsername;
        this.likeCount = likeCount;
        this.timestamp = timestamp;
        this.comments = comments;
        this.likes = likes;
    }

    protected NewsFeedItem(Builder<?> builder) {
        this.likeCount = builder.likeCount;
        this.timestamp = builder.timestamp;
        this.ownerUid = builder.ownerUid;
        this.ownerUsername = builder.ownerUsername;
        this.comments = builder.comments;
        this.likes = builder.likes;
    }

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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<String> getLikes() {
        return likes;
    }

    public void setLikes(Set<String> likes) {
        this.likes = likes;
    }

    public static class Builder<T extends Builder<T>> {
        protected int likeCount = 0;
        protected long timestamp;
        protected String ownerUid;
        protected String ownerUsername;
        protected List<Comment> comments;
        protected Set<String> likes; //Store user uid of who like this status

        public Builder() {

        }

        public T setLikeCount(int likeCount) {
            this.likeCount = likeCount;
            return (T) this;
        }

        public T setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return (T) this;
        }

        public T setOwnerUid(String ownerUid) {
            this.ownerUid = ownerUid;
            return (T) this;
        }

        public T setOwnerUsername(String ownerUsername) {
            this.ownerUsername = ownerUsername;
            return (T) this;
        }

        public T setComments(List<Comment> comments) {
            this.comments = comments;
            return (T) this;
        }

        public T setLikes(Set<String> likes) {
            this.likes = likes;
            return (T) this;
        }
    }
}
