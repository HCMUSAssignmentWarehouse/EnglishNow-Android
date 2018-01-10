package com.iceteaviet.englishnow.ui.main;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.base.NewsFeedItem;
import com.iceteaviet.englishnow.utils.AppLogger;

/**
 * Created by Genius Doan on 10/01/2018.
 */

//Design Pattern: Facade, context for execute Strategy Pattern
public class NewsFeedPostingExecutor {
    private static final String TAG = NewsFeedPostingExecutor.class.getSimpleName();
    private NewsFeedPostingTicket ticket;
    private DataManager dataManager;

    public NewsFeedPostingExecutor(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void setTicket(NewsFeedPostingTicket ticket) {
        this.ticket = ticket;
    }

    public void execute() {
        NewsFeedItem item = ticket.buildNewsFeedItem();
        if (item != null)
            ticket.post(dataManager, item);
        else
            AppLogger.e(TAG, "Cannot post newsfeed item");
    }
}
