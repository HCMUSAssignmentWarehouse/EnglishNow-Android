package com.iceteaviet.englishnow.ui.auth.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.newsfeed.view.NewsFeedActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Genius Doan on 12/24/2017.
 */

public class PostLoginDialog extends DialogFragment {
    public static final String TAG = RegisterDialog.class.getSimpleName();

    @BindView(R.id.btn_find_now) protected Button findNowButton;
    @BindView(R.id.btn_skip) protected Button skipButton;

    static PostLoginDialog newInstance() {
        PostLoginDialog d = new PostLoginDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        d.setArguments(args);

        return d;
    }

    public static PostLoginDialog showDialog(Activity activity) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PostLoginDialog dialog = PostLoginDialog.newInstance();
        dialog.show(ft, TAG);

        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.dialog_post_sign_in, container, false);
        ButterKnife.bind(this, convertView);

        findNowButton.setOnClickListener(view -> onFindNowButtonClicked());
        skipButton.setOnClickListener(view -> onSkipButtonClicked());

        return convertView;
    }

    private void onFindNowButtonClicked() {
        Intent intent = new Intent(getActivity(), ConversationMatchingActivity.class);
        startActivity(intent);
    }

    private void onSkipButtonClicked() {
        Intent intent = new Intent(getActivity(), NewsFeedActivity.class);
        startActivity(intent);
    }
}
