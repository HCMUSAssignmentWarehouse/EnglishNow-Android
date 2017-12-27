package com.iceteaviet.englishnow.ui.matching.viewmodel;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingHandler;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class ConversationMatchingViewModel extends BaseViewModel<ConversationMatchingHandler> {
    public ConversationMatchingViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }
}
