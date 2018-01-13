package com.iceteaviet.englishnow.data.remote;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;
import com.iceteaviet.englishnow.data.model.firebase.VideoCallSession;
import com.iceteaviet.englishnow.data.remote.firebase.VideoCallSessionDataSource;
import com.iceteaviet.englishnow.utils.AppLogger;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Genius Doan on 11/01/2018.
 */

//Facade
public class ConversationMatchingHandler {
    private DataManager appDataManager;
    private VideoCallSessionDataSource repository;
    private PublishSubject<OpenTokRoom> matchingSubject;
    private OpenTokRoom room = null;

    //TODO: Support disposable and schedulers
    public ConversationMatchingHandler(DataManager dataManager, VideoCallSessionDataSource dataSource) {
        appDataManager = dataManager;
        repository = dataSource;
        matchingSubject = PublishSubject.create();
    }

    public PublishSubject<OpenTokRoom> getMatchingSubject() {
        return matchingSubject;
    }

    public void doConversationMatching(String uid) {
        AppLogger.d("Genius", "doConversationMatching");
        appDataManager.doConversationMatching(uid)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String opponentUid) throws Exception {
                        createConversationRoom(uid, opponentUid);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        matchingSubject.onError(throwable);
                    }
                });
    }

    private void createConversationRoom(String uid, String opponentUid) {
        AppLogger.d("Genius", "createConversationRoom");
        Single<OpenTokRoom> roomSingle = appDataManager.getOpenTokRoomInfo(buildRoomName(uid, opponentUid));
        roomSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(openTokRoom -> {
                    waitForSessionCreated(openTokRoom.getSessionId());
                    createOrUpdateSession(openTokRoom, uid);
                }, throwable -> {
                    throwable.printStackTrace();
                    matchingSubject.onError(throwable);
                });
    }

    private void waitForSessionCreated(String sessionId) {
        AppLogger.d("Genius", "waitForSessionCreated");
        Disposable d = repository.fetch(sessionId)
                .subscribe(session -> {
                    if (session.getSessionId() != null && !session.getSessionId().isEmpty()
                            && session.getSpeakerToken() != null && !session.getSpeakerToken().isEmpty()
                            && session.getLearnerToken() != null && !session.getLearnerToken().isEmpty()) {
                        //Start call
                        matchingSubject.onNext(room);
                        matchingSubject.onComplete();
                    } else {
                        //Continue waiting
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    matchingSubject.onError(throwable);
                });
    }

    private void createOrUpdateSession(OpenTokRoom openTokRoom, String uid) {
        AppLogger.d("Genius", "waitForSessionCreated");
        repository.fetchOnce(openTokRoom.getSessionId())
                .subscribe(session -> {
                    if (session == null || session.getSessionId() == null || session.getSessionId().isEmpty()) {
                        session = new VideoCallSession();
                        session.setSpeakerUid(uid);
                        session.setSpeakerToken(openTokRoom.getToken());
                        session.setSessionId(openTokRoom.getSessionId());
                    } else {
                        session.setLearnerUid(uid);
                        session.setLearnerToken(openTokRoom.getToken());
                    }

                    this.room = openTokRoom;

                    repository.createOrUpdate(session);
                }, throwable -> {
                    throwable.printStackTrace();
                    matchingSubject.onError(throwable);
                });
    }

    private String buildRoomName(String uid, String opponentUid) {
        if (uid.compareTo(opponentUid) > 0)
            return uid.concat(opponentUid);
        else
            return opponentUid.concat(uid);
    }
}
