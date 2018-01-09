package com.iceteaviet.englishnow.ui.videocall.viewmodel;

import android.content.Context;

import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.videocall.VideoCallNavigator;
import com.iceteaviet.englishnow.utils.AppLogger;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;
import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

/**
 * Created by Genius Doan on 09/01/2018.
 */

public class VideoCallViewModel extends BaseViewModel<VideoCallNavigator>
        implements Session.SessionListener, PublisherKit.PublisherListener {
    private static final String TAG = VideoCallViewModel.class.getSimpleName();

    private Context context;
    private Session mSession;
    private Publisher mPublisher;
    private Subscriber mSubscriber;


    public VideoCallViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void initializeSession(Context context, String apiKey, String sessionId) {
        this.context = context;
        mSession = new Session.Builder(context, apiKey, sessionId).build();
        mSession.setSessionListener(this);
    }

    public void connect(String token) {
        mSession.connect(token);
    }

    @Override
    public void onConnected(Session session) {
        AppLogger.i(TAG, "Session Connected");

        mPublisher = new Publisher.Builder(context).build();
        mPublisher.setPublisherListener(this);

        getNavigator().setPublisherCallView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        AppLogger.e("onDisconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(context, stream).build();
            mSession.subscribe(mSubscriber);
            getNavigator().setSubscriberCallView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        if (mSubscriber != null) {
            mSubscriber = null;
            getNavigator().removeAllSubscriberViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        AppLogger.e("onError");
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        AppLogger.d("onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        AppLogger.e("onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        AppLogger.e("onError");
    }
}
