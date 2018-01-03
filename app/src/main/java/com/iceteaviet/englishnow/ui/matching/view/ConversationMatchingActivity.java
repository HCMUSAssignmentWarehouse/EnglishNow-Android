package com.iceteaviet.englishnow.ui.matching.view;

import android.os.Bundle;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityConversationMatchingBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.matching.ConversationMatchingNavigator;
import com.iceteaviet.englishnow.ui.matching.viewmodel.ConversationMatchingViewModel;

import javax.inject.Inject;

public class ConversationMatchingActivity extends BaseActivity<ActivityConversationMatchingBinding, ConversationMatchingViewModel> implements ConversationMatchingNavigator {

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
