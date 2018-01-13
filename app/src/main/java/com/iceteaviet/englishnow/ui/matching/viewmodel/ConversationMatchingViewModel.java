package com.iceteaviet.englishnow.ui.matching.viewmodel;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;
import com.iceteaviet.englishnow.data.remote.ConversationMatchingHandler;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingNavigator;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 12/27/2017.
 */

public class ConversationMatchingViewModel extends BaseViewModel<ConversationMatchingNavigator> {
    private static final String TAG = ConversationMatchingViewModel.class.getSimpleName();
    private ConversationMatchingHandler handler;
    private boolean isFinding = false;

    public ConversationMatchingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        handler = new ConversationMatchingHandler(getDataManager(), getDataManager().getVideoCallSessionRepository());
        getCompositeDisposable().add(handler.getMatchingSubject()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Consumer<OpenTokRoom>() {
                    @Override
                    public void accept(OpenTokRoom room) throws Exception {
                        //Finish -> start video call activity
                        getNavigator().navigateToVideoCallScreen(room.getSessionId(), room.getToken());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getNavigator().handleError(throwable);
                    }
                }));
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

    }

    public void startFinding() {
        handler.doConversationMatching(getDataManager().getCurrentUserUid());
    }
}
