package com.iceteaviet.englishnow.data.model.others;

import com.iceteaviet.englishnow.data.model.firebase.Status;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class StatusItemData {
    private Status status;
    private String avatarUrl;

    public StatusItemData(Status status, String avatarUrl) {
        this.status = status;
        this.avatarUrl = avatarUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
