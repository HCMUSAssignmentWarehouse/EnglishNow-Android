package com.iceteaviet.englishnow.ui.others.view;

import android.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.DialogPostSignInBinding;
import com.iceteaviet.englishnow.ui.auth.view.RegisterDialog;
import com.iceteaviet.englishnow.ui.base.BaseDialog;
import com.iceteaviet.englishnow.ui.main.view.MainActivity;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.others.PostLoginNavigator;
import com.iceteaviet.englishnow.ui.others.viewmodel.PostLoginViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Genius Doan on 12/24/2017.
 */

public class PostLoginDialog extends BaseDialog implements PostLoginNavigator {
    public static final String TAG = RegisterDialog.class.getSimpleName();

    @Inject
    PostLoginViewModel postLoginViewModel;

    DialogPostSignInBinding dialogPostSignInBinding;

    public static PostLoginDialog newInstance() {
        PostLoginDialog d = new PostLoginDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        d.setArguments(args);

        return d;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dialogPostSignInBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_post_sign_in, container, false);
        View convertView = dialogPostSignInBinding.getRoot();

        AndroidInjection.inject(this);

        dialogPostSignInBinding.setViewModel(postLoginViewModel);
        postLoginViewModel.setNavigator(this);

        return convertView;
    }

    @Override
    public void navigateToConversationMatchingScreen() {
        Intent intent = new Intent(getActivity(), ConversationMatchingActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToNewsFeedScreen() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }
}
