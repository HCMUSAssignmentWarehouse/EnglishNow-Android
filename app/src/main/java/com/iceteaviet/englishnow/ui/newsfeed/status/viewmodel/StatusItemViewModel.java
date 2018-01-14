package com.iceteaviet.englishnow.ui.newsfeed.status.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.iceteaviet.englishnow.data.model.firebase.Status;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public class StatusItemViewModel {
    private final ObservableField<String> avatarUrl = new ObservableField<>();
    private final ObservableField<Status> status = new ObservableField<>();
    private final ObservableBoolean isShowPhoto = new ObservableBoolean(false);

    public StatusItemViewModel(Status status) {
        this.status.set(status);
        if (status.getPhotoUrl() == null || status.getPhotoUrl().isEmpty())
            isShowPhoto.set(false);
        else
            isShowPhoto.set(true);
    }

    public ObservableField<Status> getStatus() {
        return status;
    }

    public ObservableField<String> getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl.set(avatarUrl);
    }

    public ObservableBoolean getIsShowPhoto() {
        return isShowPhoto;
    }
}
