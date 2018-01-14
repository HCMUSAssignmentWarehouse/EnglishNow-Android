package com.iceteaviet.englishnow.ui.newsfeed.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.FragmentNewsfeedBinding;
import com.iceteaviet.englishnow.ui.base.BaseFragment;
import com.iceteaviet.englishnow.ui.main.view.StatusComposerDialog;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedNavigator;
import com.iceteaviet.englishnow.ui.newsfeed.NewsFeedPagerAdapter;
import com.iceteaviet.englishnow.ui.newsfeed.viewmodel.NewsFeedViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

/**
 * Created by Genius Doan on 03/01/2018.
 */

public class NewsFeedFragment extends BaseFragment<FragmentNewsfeedBinding, NewsFeedViewModel> implements NewsFeedNavigator, HasFragmentInjector {
    public static final String TAG = NewsFeedFragment.class.getSimpleName();

    @Inject
    protected NewsFeedViewModel statusViewModel;
    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    NewsFeedPagerAdapter pagerAdapter;
    private FragmentNewsfeedBinding viewBinding;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static NewsFeedFragment newInstance() {
        Bundle args = new Bundle();
        NewsFeedFragment fragment = new NewsFeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinding = getViewDataBinding();
        setup();
    }

    @Override
    public NewsFeedViewModel getViewModel() {
        return statusViewModel;
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
    public void navigateBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setup() {
        viewPager = viewBinding.newsfeedViewPager;
        tabLayout = viewBinding.tabLayout;

        viewBinding.fabCompose.setOnClickListener(view -> {
            StatusComposerDialog dialog = StatusComposerDialog.newInstance();
            dialog.setOnPostedListener(body -> {
                //Trigger refresh
            });

            dialog.show(getFragmentManager());
        });

        pagerAdapter.setCount(2);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.status)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.video_status)));

        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
