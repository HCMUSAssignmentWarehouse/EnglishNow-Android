package com.iceteaviet.englishnow.data.remote.firebase.newsfeed;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.base.NewsFeedItem;

/**
 * Created by Genius Doan on 10/01/2018.
 */

//Skeleton of Strategy Pattern
public interface NewsFeedPostingTicket<T extends NewsFeedItem> {
    T buildNewsFeedItem();

    void post(DataManager dataManager, T item);
}
