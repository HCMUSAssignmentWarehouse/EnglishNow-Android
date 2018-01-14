package com.iceteaviet.englishnow.data.remote;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.api.OpenTokRoom;
import com.iceteaviet.englishnow.data.model.firebase.VideoCallSession;
import com.iceteaviet.englishnow.data.remote.firebase.videocall.VideoCallSessionDataSource;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Genius Doan on 11/01/2018.
 * <p>
 * Facade design pattern, Observer design pattern (using Rx)
 * Provides a simplified interface to manage and execute the conversation matching operation
 */

public class ConversationMatchingHandler {
    private static final String TAG = ConversationMatchingHandler.class.getSimpleName();
    private DataManager appDataManager;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;
    private VideoCallSessionDataSource repository;
    private PublishSubject<OpenTokRoom> matchingSubject;
    private OpenTokRoom currentRoom = null;

    public ConversationMatchingHandler(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, VideoCallSessionDataSource dataSource) {
        appDataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        repository = dataSource;
        matchingSubject = PublishSubject.create();
    }

    public PublishSubject<OpenTokRoom> getMatchingSubject() {
        return matchingSubject;
    }

    public void startConversationMatching(String uid) {
        //Use Firebase helper to do conversation matching to match uid of learner and speaker
        Single<String> matchingSingle = appDataManager.doConversationMatching(uid);
        matchingSingle
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Add to composite disposable to dispose together with other observer when app stopped
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(String opponentUid) {
                        //Complete, remove learner from available list (available_learners node on Firebase)
                        appDataManager.removeLearnerFromAvailableList(uid);

                        //Create conversation room
                        createConversationRoom(uid, opponentUid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        matchingSubject.onError(e);
                    }
                });
    }

    public void stopConversationMatching(String uid) {
        appDataManager.removeLearnerFromAvailableList(uid);
    }

    private void createConversationRoom(String uid, String opponentUid) {
        //Build unique room name base on uid of learner and speaker
        String roomName = buildRoomName(uid, opponentUid);

        //Use AppApiHelper to create and get room info
        Single<OpenTokRoom> roomSingle = appDataManager.getOpenTokRoomInfo(roomName);
        roomSingle.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<OpenTokRoom>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(OpenTokRoom openTokRoom) {
                        //Start a observer to know whenever a client connect to this session
                        waitForSessionCreated(openTokRoom.getSessionId());

                        //Create session or update if already exist
                        createOrUpdateSession(openTokRoom, uid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        matchingSubject.onError(e);
                    }
                });
    }

    private void waitForSessionCreated(String sessionId) {
        Observable<VideoCallSession> fetchSessionObservable = repository.fetch(sessionId);
        fetchSessionObservable
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<VideoCallSession>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(VideoCallSession session) {
                        if (session.getSessionId() != null && !session.getSessionId().isEmpty()
                                && session.getSpeakerToken() != null && !session.getSpeakerToken().isEmpty()
                                && session.getLearnerToken() != null && !session.getLearnerToken().isEmpty()) {
                            //Start call
                            matchingSubject.onNext(currentRoom);
                            matchingSubject.onComplete();
                        } else {
                            //Continue waiting
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        matchingSubject.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void createOrUpdateSession(OpenTokRoom openTokRoom, String uid) {
        //Use FirebaseHelper, fetch session to check whether it already created or not
        Single<VideoCallSession> fetchSessionSingle = repository.fetchOnce(openTokRoom.getSessionId());
        fetchSessionSingle
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<VideoCallSession>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(VideoCallSession session) {
                        if (session == null || session.getSessionId() == null || session.getSessionId().isEmpty()) {
                            //Session does not exist -> current user is publisher -> create new session and wait
                            session = new VideoCallSession();
                            session.setSpeakerUid(uid);
                            session.setSpeakerToken(openTokRoom.getToken());
                            session.setSessionId(openTokRoom.getSessionId());
                        } else {
                            //Already exist a session -> current user is subscriber -> update current session
                            session.setLearnerUid(uid);
                            session.setLearnerToken(openTokRoom.getToken());
                        }

                        //Update
                        ConversationMatchingHandler.this.currentRoom = openTokRoom;
                        repository.createOrUpdate(session);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        matchingSubject.onError(e);
                    }
                });
    }

    private String buildRoomName(String uid, String opponentUid) {
        if (uid.compareTo(opponentUid) > 0)
            return uid.concat(opponentUid);
        else
            return opponentUid.concat(uid);
    }
}
