package com.iceteaviet.englishnow.ui.main.view;

import android.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.BuildConfig;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityMainBinding;
import com.iceteaviet.englishnow.databinding.NavigationHeaderMainBinding;
import com.iceteaviet.englishnow.ui.about.view.AboutActivity;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.main.MainHandler;
import com.iceteaviet.englishnow.ui.main.viewmodel.MainViewModel;
import com.iceteaviet.englishnow.ui.profile.view.ProfileActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainHandler, HasFragmentInjector {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = getViewDataBinding();
        mainViewModel.setHandler(this);
        setup();
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
        subscribeToLiveData();
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

    }

    private void showProfileFragment() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void showAboutFragment() {
        startActivity(new Intent(this, AboutActivity.class));
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
}