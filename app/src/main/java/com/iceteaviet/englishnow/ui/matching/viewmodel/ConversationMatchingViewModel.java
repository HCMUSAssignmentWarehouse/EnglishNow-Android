package com.iceteaviet.englishnow.ui.matching.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.remote.ConversationMatchingHandler;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class ConversationMatchingViewModel extends BaseViewModel<ConversationMatchingNavigator> {
    private static final String TAG = ConversationMatchingViewModel.class.getSimpleName();
    private ConversationMatchingHandler handler;
    private boolean isFinding = false;

    public ConversationMatchingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        handler = new ConversationMatchingHandler(dataManager, schedulerProvider, getCompositeDisposable(), dataManager.getVideoCallSessionRepository());

        //Subscribe to data-stream to know when the matching complete and get the data
        getCompositeDisposable().add(handler.getMatchingSubject()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(room -> {
                    //Finish -> start video call activity
                    getNavigator().navigateToVideoCallScreen(room.getSessionId(), room.getToken());
                }, throwable -> getNavigator().handleError(throwable)));
    }

    public void onFindButtonClicked() {
        if (!isFinding) {
            getNavigator().changeViewsToFindingMode();
            if (getNavigator().selfCheckRequiredPermissions()) {
                startFinding();
            } else {
                getNavigator().requestPermissions();
            }
        } else {
            getNavigator().changeViewsToNormalMode();
            stopFinding();
        }

        isFinding = !isFinding;
    }

    private void stopFinding() {
        handler.stopConversationMatching(getDataManager().getCurrentUserUid());
    }

    public void startFinding() {
        handler.startConversationMatching(getDataManager().getCurrentUserUid());
    }
}
