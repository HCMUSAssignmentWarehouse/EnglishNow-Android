package com.iceteaviet.englishnow.data.model.firebase.base;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@IgnoreExtraProperties
public abstract class AbstractUser {
    @PropertyName("email")
    protected String email;
    @PropertyName("username")
    protected String username;
    @PropertyName("profile_pic")
    protected String profilePic;

    protected AbstractUser() {

    }

    public AbstractUser(String email, String username, String profilePic) {
        this.email = email;
        this.username = username;
        this.profilePic = profilePic;
    }

    public AbstractUser(Builder builder) {
        this.email = builder.email;
        this.username = builder.username;
        this.profilePic = builder.profilePic;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @PropertyName("profile_pic")
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public static class Builder<T extends Builder<T>> {
        protected String email;
        protected String username;
        protected String profilePic;

        public T setUsername(String username) {
            this.username = username;
            return (T) this;
        }

        public T setEmail(String email) {
            this.email = email;
            return (T) this;
        }

        public T setProfilePic(String profilePic) {
            this.profilePic = profilePic;
            return (T) this;
        }
    }
}
