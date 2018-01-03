package com.iceteaviet.englishnow.ui.newsfeed.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.databinding.FragmentNewsfeedBinding;
import com.iceteaviet.englishnow.ui.base.BaseFragment;
import com.iceteaviet.englishnow.ui.main.NewsFeedAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedNavigator;
import com.iceteaviet.englishnow.ui.newsfeed.viewmodel.NewsFeedViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Genius Doan on 03/01/2018.
 */

public class NewsFeedFragment extends BaseFragment<FragmentNewsfeedBinding, NewsFeedViewModel> implements NewsFeedNavigator {

    public static final String TAG = "NewsFeedFragment";

    @Inject
    NewsFeedViewModel newsFeedViewModel;

    private FragmentNewsfeedBinding viewBinding;


    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;

    public static NewsFeedFragment newInstance() {
        Bundle args = new Bundle();
        NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsFeedViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding = getViewDataBinding();
        setup();
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
        return R.layout.fragment_newsfeed;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setup() {
        setupNewsFeedItemsRecyclerView();
        subscribeToLiveData();
    }

    private void setupNewsFeedItemsRecyclerView() {
        recyclerView = viewBinding.rvStatusContainer;
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        adapter = new NewsFeedAdapter(getBaseActivity());
        recyclerView.setAdapter(adapter);
    }

    private void subscribeToLiveData() {
        newsFeedViewModel.getStatusItemsLiveData().observe(getBaseActivity(), new Observer<List<StatusItemData>>() {
            @Override
            public void onChanged(@Nullable List<StatusItemData> statusItemData) {
                newsFeedViewModel.setNewsFeedItems(statusItemData);
            }
        });
    }
}
