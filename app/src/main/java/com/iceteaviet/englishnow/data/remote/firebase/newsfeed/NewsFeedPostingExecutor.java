package com.iceteaviet.englishnow.data.remote.firebase.newsfeed;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.base.NewsFeedItem;
import com.iceteaviet.englishnow.utils.AppLogger;

/**
 * Created by Genius Doan on 10/01/2018.
 * <p>
 * Use for execute a newsfeed item posting ticket.
 * Design pattern applied: Singleton, Facade, context to execute Strategy Pattern
 */

public class NewsFeedPostingExecutor {
    private static final String TAG = NewsFeedPostingExecutor.class.getSimpleName();
    private static NewsFeedPostingExecutor instance = null;
    private DataManager dataManager;

    private NewsFeedPostingExecutor() {
        //Singleton
    }

    public static NewsFeedPostingExecutor getInstance() {
        //Lazy init
        if (instance == null) {
            instance = new NewsFeedPostingExecutor();
        }

        return instance;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void execute(NewsFeedPostingTicket ticket) {
        NewsFeedItem item = ticket.buildNewsFeedItem();
        if (item != null)
            ticket.post(dataManager, item);
        else
            AppLogger.e(TAG, "Cannot post newsfeed item");
    }
}
