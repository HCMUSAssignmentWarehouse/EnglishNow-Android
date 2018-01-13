package com.iceteaviet.englishnow.ui.splash.view;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivitySplashBinding;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.intro.view.IntroActivity;
import com.iceteaviet.englishnow.ui.others.view.PostLoginDialog;
import com.iceteaviet.englishnow.ui.splash.SplashNavigator;
import com.iceteaviet.englishnow.ui.splash.viewmodel.SplashViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator, HasFragmentInjector {
    @Inject
    protected SplashViewModel splashViewModel;

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel.setNavigator(this);
        activitySplashBinding = getViewDataBinding();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        splashViewModel.startDataLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public SplashViewModel getViewModel() {
        return splashViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void navigateToIntroScreen() {
        //Show intro activity
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginScreen() {
        //start login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToPostLoginScreen() {
        //start navigate activity
        PostLoginDialog d = PostLoginDialog.newInstance();
        d.show(getFragmentManager());
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
