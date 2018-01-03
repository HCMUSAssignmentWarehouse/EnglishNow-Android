package com.iceteaviet.englishnow.ui.matching.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class ConversationMatchingViewModel extends BaseViewModel<ConversationMatchingNavigator> {
    public ConversationMatchingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
