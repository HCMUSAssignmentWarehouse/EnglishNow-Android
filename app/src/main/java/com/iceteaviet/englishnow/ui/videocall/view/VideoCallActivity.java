package com.iceteaviet.englishnow.ui.videocall.view;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityVideoCallBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.videocall.VideoCallNavigator;
import com.iceteaviet.englishnow.ui.videocall.viewmodel.VideoCallViewModel;
import com.opentok.android.OpentokError;

import javax.inject.Inject;

public class VideoCallActivity extends BaseActivity<ActivityVideoCallBinding, VideoCallViewModel> implements VideoCallNavigator {

    @Inject
    protected VideoCallViewModel videoCallViewModel;

    private ActivityVideoCallBinding videoCallBinding;

    private FrameLayout publisherViewContainer;
    private FrameLayout subscriberViewContainer;
    private ImageButton cameraButton, microButton, hangUpButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoCallViewModel.setNavigator(this);
        videoCallBinding = getViewDataBinding();

        bindViews();

        videoCallViewModel.setSessionId(getIntent().getStringExtra(ConversationMatchingActivity.EXTRA_SESSION_ID));
        videoCallViewModel.setToken(getIntent().getStringExtra(ConversationMatchingActivity.EXTRA_SESSION_TOKEN));

        // initialize and connect to the session
        videoCallViewModel.initializeSession(this, getString(R.string.tokbox_api_key));
        videoCallViewModel.connect();
    }

    private void bindViews() {
        publisherViewContainer = videoCallBinding.publisherContainer;
        subscriberViewContainer = videoCallBinding.subscriberContainer;
        cameraButton = videoCallBinding.btnCamera;
        microButton = videoCallBinding.btnMicro;
        hangUpButton = videoCallBinding.btnHangUp;
        progressBar = videoCallBinding.progressBar;
    }

    @Override
    public void setPublisherCallView(View view) {
        publisherViewContainer.addView(view);
        if (view instanceof GLSurfaceView) {
            ((GLSurfaceView) view).setZOrderOnTop(true);
        }
    }

    @Override
    public void setMicroButtonEnabled(boolean enabled) {
        microButton.setImageResource(enabled ? R.drawable.ic_micro : R.drawable.ic_micro_off);
    }

    @Override
    public void setCameraButtonEnabled(boolean enabled) {
        cameraButton.setImageResource(enabled ? R.drawable.ic_camera : R.drawable.ic_camera_off);
    }

    @Override
    public void handleOpentokError(OpentokError error) {
        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rl_root_view),
                error.getMessage(), Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    @Override
    public void onReconnecting() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReconnected() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void closeCallScreen() {
        hangUpButton.setEnabled(false);
        new Handler().postDelayed(() -> finish(), 1500);
    }

    @Override
    public void navigateToRatingScreen() {

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
