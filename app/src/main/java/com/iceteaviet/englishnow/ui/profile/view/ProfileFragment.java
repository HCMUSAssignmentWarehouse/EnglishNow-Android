package com.iceteaviet.englishnow.ui.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.FragmentProfileBinding;
import com.iceteaviet.englishnow.ui.base.BaseFragment;
import com.iceteaviet.englishnow.ui.profile.ProfileNavigator;
import com.iceteaviet.englishnow.ui.profile.viewmodel.ProfileViewModel;

import javax.inject.Inject;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator {
    public static final String TAG = ProfileFragment.class.getSimpleName();

    @Inject
    protected ProfileViewModel profileViewModel;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel.setNavigator(this);
        profileViewModel.startLoadingData();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();
    }

    @Override
    public ProfileViewModel getViewModel() {
        return profileViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void navigateBack() {
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
    }

    private void subscribeToLiveData() {
        profileViewModel.getUserLiveData().observe(getBaseActivity(), user -> profileViewModel.setUser(user));
    }
}
