package com.iceteaviet.englishnow.data.model.base;

import com.google.firebase.database.PropertyName;

/**
 * Created by Genius Doan on 28/12/2017.
 */

public abstract class AbstractUser {
    @PropertyName("email")
    protected String email;

    @PropertyName("username")
    protected String username;

    @PropertyName("profile_pic")
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
