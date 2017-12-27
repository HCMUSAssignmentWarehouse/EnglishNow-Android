package com.iceteaviet.englishnow.ui.newsfeed.view;

import android.os.Bundle;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityNewsFeedBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedHandler;
import com.iceteaviet.englishnow.ui.newsfeed.viewmodel.NewsFeedViewModel;

import javax.inject.Inject;

public class NewsFeedActivity extends BaseActivity<ActivityNewsFeedBinding, NewsFeedViewModel> implements NewsFeedHandler {

    @Inject
    NewsFeedViewModel newsFeedViewModel;

    ActivityNewsFeedBinding newsFeedBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsFeedViewModel.setHandler(this);
        newsFeedBinding = getViewDataBinding();
    }

    @Override
    public NewsFeedViewModel getViewModel() {
        return newsFeedViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_feed;
    }
}
