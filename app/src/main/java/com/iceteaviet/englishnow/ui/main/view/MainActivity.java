package com.iceteaviet.englishnow.ui.main.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.BuildConfig;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivityMainBinding;
import com.iceteaviet.englishnow.databinding.NavigationHeaderMainBinding;
import com.iceteaviet.englishnow.ui.about.view.AboutFragment;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.main.MainNavigator;
import com.iceteaviet.englishnow.ui.main.viewmodel.MainViewModel;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.newsfeed.view.NewsFeedFragment;
import com.iceteaviet.englishnow.ui.profile.view.ProfileFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasFragmentInjector {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    protected ActivityMainBinding activityMainBinding;
    private MainViewModel mainViewModel;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    private int currentMenuItemId;

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
        //unlockDrawer();
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
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setup() {
        drawerLayout = activityMainBinding.drawerView;
        toolbar = activityMainBinding.toolbar;
        navigationView = activityMainBinding.navigationView;

        setSupportActionBar(toolbar);

        setupDrawerLayout();
        setupNavigationHeader();

        //Inflate default
        currentMenuItemId = R.id.nav_item_newsfeed;
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setCheckedItem(R.id.nav_item_newsfeed);
        selectFragment(NewsFeedFragment.newInstance(), NewsFeedFragment.TAG);

        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mainViewModel.updateAppVersion(version);
        mainViewModel.onNavigationViewCreated();
    }

    private void setupDrawerLayout() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        drawerToggle = new ActionBarDrawerToggle(this,
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

        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    private void setupNavigationHeader() {
        NavigationHeaderMainBinding navigationHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.navigation_header_main, navigationView, false);

        navigationView.addHeaderView(navigationHeaderMainBinding.getRoot());

        navigationHeaderMainBinding.setViewModel(mainViewModel);

        navigationView.setNavigationItemSelectedListener(
                menuItem -> MainActivity.this.onNavigationItemSelected(menuItem));
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        //Close
        drawerLayout.closeDrawer(GravityCompat.START);

        //Only show if the selected fragment is not current fragment
        if (menuItem.getItemId() == currentMenuItemId)
            return false;

        if (menuItem.getItemId() != R.id.nav_item_about) {
            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            currentMenuItemId = menuItem.getItemId();
        }

        switch (menuItem.getItemId()) {
            case R.id.nav_item_profile:
                showProfileFragment();
                return true;
            case R.id.nav_item_newsfeed:
                showNewsFeedFragment();
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
    }

    private void showNewsFeedFragment() {
        selectFragment(NewsFeedFragment.newInstance(), NewsFeedFragment.TAG);
    }

    private void showConversationMatchingActivity() {
        startActivity(new Intent(this, ConversationMatchingActivity.class));
    }


    private void showProfileFragment() {
        selectFragment(ProfileFragment.newInstance(), ProfileFragment.TAG);
    }

    private void showAboutFragment() {
        lockDrawer();
        addFragment(AboutFragment.newInstance(), AboutFragment.TAG);
    }

    @Override
    public void navigateToLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void handleError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(activityMainBinding.flRootView, throwable.getMessage(), Snackbar.LENGTH_LONG).show();
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

    /**
     * Swaps fragments in the main content view
     */
    private void selectFragment(Fragment fragment, String tag) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                .replace(R.id.content_frame, fragment, tag)
                .commit();
    }

    private void addFragment(Fragment fragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.animator.slide_left, R.animator.slide_right)
                .add(R.id.rl_root_view, fragment, tag)
                .commit();
    }
}