package com.iceteaviet.englishnow.ui.main.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.BuildConfig;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.databinding.ActivityMainBinding;
import com.iceteaviet.englishnow.databinding.NavigationHeaderMainBinding;
import com.iceteaviet.englishnow.ui.about.view.AboutFragment;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.main.MainNavigator;
import com.iceteaviet.englishnow.ui.main.NewsFeedAdapter;
import com.iceteaviet.englishnow.ui.main.viewmodel.MainViewModel;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.profile.view.ProfileFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasFragmentInjector {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private NewsFeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = getViewDataBinding();
        mainViewModel.setNavigator(this);
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        switch (item.getItemId()) {
            case R.id.action_share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                    .remove(fragment)
                    .commit();
            unlockDrawer();
        }
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        } else {
            onFragmentDetached(AboutFragment.TAG);
        }
    }

    @Override
    public MainViewModel getViewModel() {
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        return mainViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void setup() {
        drawerLayout = activityMainBinding.drawerView;
        toolbar = activityMainBinding.toolbar;
        navigationView = activityMainBinding.navigationView;

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        setupNavigationHeader();

        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mainViewModel.updateAppVersion(version);
        mainViewModel.onNavigationViewCreated();
        setupNewsFeedItemsRecyclerView();
        subscribeToLiveData();
    }

    private void setupNewsFeedItemsRecyclerView() {
        recyclerView = activityMainBinding.rvStatusContainer;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsFeedAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    private void setupNavigationHeader() {
        NavigationHeaderMainBinding navigationHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.navigation_header_main, activityMainBinding.navigationView, false);
        activityMainBinding.navigationView.addHeaderView(navigationHeaderMainBinding.getRoot());
        navigationHeaderMainBinding.setViewModel(mainViewModel);

        navigationView.setNavigationItemSelectedListener(
                item -> {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.nav_item_profile:
                            showProfileFragment();
                            return true;
                        case R.id.nav_item_matching:
                            showConversationMatchingActivity();
                            return true;
                        case R.id.nav_item_about:
                            showAboutFragment();
                            return true;
                        case R.id.nav_item_logout:
                            mainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void subscribeToLiveData() {
        mainViewModel.getStatusItemsLiveData().observe(this, new Observer<List<StatusItemData>>() {
            @Override
            public void onChanged(@Nullable List<StatusItemData> statusItemData) {
                mainViewModel.setNewsFeedItems(statusItemData);
            }
        });
    }

    private void showConversationMatchingActivity() {
        startActivity(new Intent(this, ConversationMatchingActivity.class));
    }


    private void showProfileFragment() {
        lockDrawer();
        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                .add(R.id.clRootView, ProfileFragment.newInstance(), ProfileFragment.TAG)
                .commit();
    }

    private void showAboutFragment() {
        lockDrawer();
        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    @Override
    public void navigateToLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void lockDrawer() {
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer() {
        if (drawerLayout != null)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}