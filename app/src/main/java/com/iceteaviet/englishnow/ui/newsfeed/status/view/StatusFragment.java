package com.iceteaviet.englishnow.ui.newsfeed.status.view;

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
import com.iceteaviet.englishnow.databinding.FragmentStatusBinding;
import com.iceteaviet.englishnow.di.NewsFeedViewModelProviderFactory;
import com.iceteaviet.englishnow.ui.base.BaseFragment;
import com.iceteaviet.englishnow.ui.main.view.MainActivity;
import com.iceteaviet.englishnow.ui.newsfeed.status.StatusAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.status.StatusNavigator;
import com.iceteaviet.englishnow.ui.newsfeed.status.viewmodel.StatusViewModel;

import javax.inject.Inject;

/**
 * Created by Genius Doan on 14/01/2018.
 */

public class StatusFragment extends BaseFragment<FragmentStatusBinding, StatusViewModel> implements StatusNavigator {
    @Inject
    protected @NewsFeedViewModelProviderFactory
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    protected StatusAdapter statusAdapter;

    @Inject
    protected LinearLayoutManager mLayoutManager;

    private FragmentStatusBinding statusBinding;
    private RecyclerView recyclerView;
    private TextView placeHolderText;
    private StatusViewModel statusViewModel;

    public static StatusFragment newInstance() {

        Bundle args = new Bundle();

        StatusFragment fragment = new StatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusBinding = getViewDataBinding();
        setUp();
        subscribeToLiveData();
    }

    @Override
    public StatusViewModel getViewModel() {
        statusViewModel = ViewModelProviders.of((MainActivity) getActivity(), mViewModelFactory).get(StatusViewModel.class);
        return statusViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_status;
    }

    @Override
    public void setPlaceHolderTextVisible(boolean visible) {
        placeHolderText.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setUp() {
        recyclerView = statusBinding.rvStatusContainer;
        placeHolderText = statusBinding.tvPlaceholder;

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(statusAdapter);
    }

    private void subscribeToLiveData() {
        statusViewModel.getStatusItemsLiveData().observe(getBaseActivity(), statusItemViewModels -> {
            statusViewModel.setStatusItems(statusItemViewModels);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
