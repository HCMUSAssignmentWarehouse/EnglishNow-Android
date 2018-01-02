package com.iceteaviet.englishnow.data.model.api;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@IgnoreExtraProperties
public abstract class AbstractUser {
    protected String email;
    protected String username;
    protected String profilePic;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
