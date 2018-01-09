package com.iceteaviet.englishnow.ui.matching.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityConversationMatchingBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingNavigator;
import com.iceteaviet.englishnow.ui.matching.viewmodel.ConversationMatchingViewModel;

import javax.inject.Inject;

public class ConversationMatchingActivity extends BaseActivity<ActivityConversationMatchingBinding, ConversationMatchingViewModel> implements ConversationMatchingNavigator {

    private static final String TAG = ConversationMatchingActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;

    @Inject
    ConversationMatchingViewModel conversationMatchingViewModel;

    ActivityConversationMatchingBinding conversationMatchingBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conversationMatchingViewModel.setNavigator(this);
        conversationMatchingBinding = getViewDataBinding();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_VIDEO_APP_PERM: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    conversationMatchingViewModel.startFinding();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public boolean selfCheckRequiredPermissions() {
        return hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    @Override
    public void requestPermissions() {
        requestPermissionsSafely(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, RC_VIDEO_APP_PERM);
    }

    @Override
    public ConversationMatchingViewModel getViewModel() {
        return conversationMatchingViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_conversation_matching;
    }
}
