package com.iceteaviet.englishnow.data.remote.firebase.newsfeed;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.Status;

/**
 * Created by Genius Doan on 10/01/2018.
 * <p>
 * Strategy options for posting text/image status
 */

public class StatusPostingTicket implements NewsFeedPostingTicket<Status> {
    private String ownerUid;
    private String displayName;
    private String content;
    private String photoUrl = ""; //Optional

    public StatusPostingTicket(String ownerUid, String displayName, String content) {
        this.ownerUid = ownerUid;
        this.displayName = displayName;
        this.content = content;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public Status buildNewsFeedItem() {
        Status.Builder builder = new Status.Builder()
                .setOwnerUid(ownerUid)
                .setOwnerUsername(displayName)
                .setContent(content)
                .setPhotoUrl(photoUrl)
                .setTimestamp(System.currentTimeMillis());


        return builder.build();
    }

    @Override
    public void post(DataManager dataManager, Status status) {
        dataManager.getNewsFeedItemRepository().createOrUpdate(status);
    }
}
