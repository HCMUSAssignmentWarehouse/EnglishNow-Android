package com.iceteaviet.englishnow.ui.videocall.view;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityVideoCallBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.videocall.VideoCallNavigator;
import com.iceteaviet.englishnow.ui.videocall.viewmodel.VideoCallViewModel;

import javax.inject.Inject;

public class VideoCallActivity extends BaseActivity<ActivityVideoCallBinding, VideoCallViewModel> implements VideoCallNavigator {

    @Inject
    VideoCallViewModel videoCallViewModel;

    ActivityVideoCallBinding videoCallBinding;

    private FrameLayout publisherViewContainer;
    private FrameLayout subscriberViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoCallViewModel.setNavigator(this);
        videoCallBinding = getViewDataBinding();

        publisherViewContainer = videoCallBinding.publisherContainer;
        subscriberViewContainer = videoCallBinding.subscriberContainer;

        videoCallViewModel.setSessionId(getIntent().getStringExtra(ConversationMatchingActivity.EXTRA_SESSION_ID));
        videoCallViewModel.setToken(getIntent().getStringExtra(ConversationMatchingActivity.EXTRA_SESSION_TOKEN));

        // initialize and connect to the session
        videoCallViewModel.initializeSession(this, getString(R.string.tokbox_api_key));
        videoCallViewModel.connect();
    }

    @Override
    public void setPublisherCallView(View view) {
        publisherViewContainer.addView(view);
    }

    @Override
    public void setSubscriberCallView(View view) {
        subscriberViewContainer.addView(view);
    }

    @Override
    public void removeAllSubscriberViews() {
        subscriberViewContainer.removeAllViews();
    }

    @Override
    public VideoCallViewModel getViewModel() {
        return videoCallViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_call;
    }
}
