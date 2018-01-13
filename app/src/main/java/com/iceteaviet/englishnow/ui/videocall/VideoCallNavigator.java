package com.iceteaviet.englishnow.ui.videocall;

import android.view.View;

import com.opentok.android.OpentokError;

/**
 * Created by Genius Doan on 09/01/2018.
 */

public interface VideoCallNavigator {
    void setPublisherCallView(View view);

    void setSubscriberCallView(View view);

    void removeAllSubscriberViews();

    void setMicroButtonEnabled(boolean enabled);

    void setCameraButtonEnabled(boolean enabled);

    void handleOpentokError(OpentokError error);

    void onReconnecting();

    void onReconnected();

    void closeCallScreen();

    void navigateToRatingScreen();
}
