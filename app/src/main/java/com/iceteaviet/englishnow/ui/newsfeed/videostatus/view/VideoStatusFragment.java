package com.iceteaviet.englishnow.ui.newsfeed.videostatus.view;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.FragmentVideoStatusBinding;
import com.iceteaviet.englishnow.di.NewsFeedViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.base.BaseFragment;
import com.iceteaviet.englishnow.ui.main.view.MainActivity;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.VideoStatusAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.VideoStatusNavigator;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.viewmodel.VideoStatusViewModel;

import javax.inject.Inject;

/**
 * Created by Genius Doan on 14/01/2018.
 */

public class VideoStatusFragment extends BaseFragment<FragmentVideoStatusBinding, VideoStatusViewModel> implements VideoStatusNavigator {

    @Inject
    protected @NewsFeedViewModelProviderFactory
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    protected VideoStatusAdapter videoStatusAdapter;

    @Inject
    protected LinearLayoutManager mLayoutManager;

    private VideoStatusViewModel videoStatusViewModel;
    private FragmentVideoStatusBinding videoStatusBinding;
    private RecyclerView recyclerView;
    private TextView placeHolderText;

    public static VideoStatusFragment newInstance() {
        Bundle args = new Bundle();
        VideoStatusFragment fragment = new VideoStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoStatusViewModel.setNavigator(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoStatusBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        videoStatusViewModel.getStatusItemsLiveData().observe(getBaseActivity(), statusItemViewModels -> videoStatusViewModel.setStatusItems(statusItemViewModels));
    }

    @Override
    public VideoStatusViewModel getViewModel() {
        videoStatusViewModel = ViewModelProviders.of((MainActivity) getActivity(), mViewModelFactory).get(VideoStatusViewModel.class);
        return videoStatusViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_status;
    }

    @Override
    public void setPlaceHolderTextVisible(boolean visible) {
        placeHolderText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setUp() {
        recyclerView = videoStatusBinding.rvStatusContainer;
        placeHolderText = videoStatusBinding.tvPlaceholder;

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(videoStatusAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
