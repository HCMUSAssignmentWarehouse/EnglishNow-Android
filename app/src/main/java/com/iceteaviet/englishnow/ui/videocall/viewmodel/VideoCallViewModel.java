package com.iceteaviet.englishnow.ui.videocall.viewmodel;

import android.content.Context;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.videocall.VideoCallNavigator;
import com.iceteaviet.englishnow.utils.AppLogger;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;
import com.opentok.android.BaseVideoRenderer;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;
import com.opentok.android.SubscriberKit;

/**
 * Created by Genius Doan on 09/01/2018.
 */

public class VideoCallViewModel extends BaseViewModel<VideoCallNavigator>
        implements Session.SessionListener, PublisherKit.PublisherListener, SubscriberKit.SubscriberListener, Session.ReconnectionListener {
    private static final String TAG = VideoCallViewModel.class.getSimpleName();

    private Context context;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private String sessionId;
    private String token;


    public VideoCallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void onHangUpButtonClicked() {
        mSession.disconnect();
    }

    public void onCameraButtonClicked() {
        boolean isCameraOn = mPublisher.getPublishVideo();
        isCameraOn = !isCameraOn;

        //Update
        mPublisher.setPublishVideo(isCameraOn);
        getNavigator().setCameraButtonEnabled(isCameraOn);
    }

    public void onMicroButtonClicked() {
        boolean isMicOn = mPublisher.getPublishAudio();
        isMicOn = !isMicOn;

        //Update
        mPublisher.setPublishAudio(isMicOn);
        getNavigator().setMicroButtonEnabled(isMicOn);
    }

    public void initializeSession(Context context, String apiKey) {
        this.context = context;
        mSession = new Session.Builder(context, apiKey, sessionId).build();
        mSession.setSessionListener(this);
        mSession.setReconnectionListener(this);
    }

    public void connect() {
        mSession.connect(token);
    }

    @Override
    public void onConnected(Session session) {
        AppLogger.i(TAG, "Session Connected");

        // initialize Publisher and set this object to listen to Publisher events
        mPublisher = new Publisher.Builder(context)
                .name("Stranger")
                .build();
        mPublisher.setPublisherListener(this);

        // set publisher video style to fill view
        getNavigator().setPublisherCallView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        //called when the client disconnects from the OpenTok session.
        AppLogger.e("onDisconnected");
        getNavigator().closeCallScreen();
        getDataManager().getVideoCallSessionRepository().remove(session.getSessionId());
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(context, stream)
                    .build();
            mSubscriber.getRenderer().setStyle(BaseVideoRenderer.STYLE_VIDEO_SCALE, BaseVideoRenderer.STYLE_VIDEO_FILL);
            mSubscriber.setSubscriberListener(this);
            mSession.subscribe(mSubscriber);
            getNavigator().setSubscriberCallView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        //Called when streams published BY OTHER clients leave this session
        if (mSubscriber != null) {
            mSubscriber = null;
            getNavigator().removeAllSubscriberViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        getNavigator().handleOpentokError(opentokError);
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        AppLogger.d("onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        //called when the publisher stops streaming to the session
        AppLogger.e("onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        getNavigator().handleOpentokError(opentokError);
    }

    @Override
    public void onConnected(SubscriberKit subscriberKit) {
        AppLogger.d(TAG, "onConnected: Subscriber connected. Stream: " + subscriberKit.getStream().getStreamId());
    }

    @Override
    public void onDisconnected(SubscriberKit subscriberKit) {
        //a client drops a connection to a subscribed stream (for example, due to a drop in network connectivity in either client)
        AppLogger.d(TAG, "onDisconnected: Subscriber disconnected. Stream: " + subscriberKit.getStream().getStreamId());
    }

    @Override
    public void onError(SubscriberKit subscriberKit, OpentokError opentokError) {
        getNavigator().handleOpentokError(opentokError);
    }

    @Override
    public void onReconnecting(Session session) {
        getNavigator().onReconnecting();
    }

    @Override
    public void onReconnected(Session session) {
        getNavigator().onReconnected();
    }
}
