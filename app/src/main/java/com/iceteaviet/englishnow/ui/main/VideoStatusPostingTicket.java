package com.iceteaviet.englishnow.ui.main;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.VideoStatus;

/**
 * Created by Genius Doan on 10/01/2018.
 */

public class VideoStatusPostingTicket implements NewsFeedPostingTicket<VideoStatus> {
    private String ownerUid;
    private String displayName;
    private String videoUrl;

    public VideoStatusPostingTicket(String uid, String userDisplayName, String videoUrl) {
        ownerUid = uid;
        displayName = userDisplayName;
        this.videoUrl = videoUrl;
    }

    @Override
    public VideoStatus buildNewsFeedItem() {
        return null;
    }

    @Override
    public void post(DataManager dataManager, VideoStatus item) {

    }
}
