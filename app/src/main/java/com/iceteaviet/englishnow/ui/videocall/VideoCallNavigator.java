package com.iceteaviet.englishnow.ui.videocall;

import android.view.View;

/**
 * Created by Genius Doan on 09/01/2018.
 */

public interface VideoCallNavigator {
    void setPublisherCallView(View view);

    void setSubscriberCallView(View view);

    void removeAllSubscriberViews();
}
