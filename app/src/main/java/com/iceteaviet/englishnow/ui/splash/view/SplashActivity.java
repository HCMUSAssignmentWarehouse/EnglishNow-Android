package com.iceteaviet.englishnow.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.iceteaviet.englishnow.BR;
import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.databinding.ActivitySplashBinding;
import com.iceteaviet.englishnow.ui.base.BaseActivity;
import com.iceteaviet.englishnow.ui.intro.view.IntroActivity;
import com.iceteaviet.englishnow.ui.login.view.LoginActivity;
import com.iceteaviet.englishnow.ui.login.view.PostLoginDialog;
import com.iceteaviet.englishnow.ui.splash.SplashHandler;
import com.iceteaviet.englishnow.ui.splash.viewmodel.SplashViewModel;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashHandler {
    @Inject
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel.setHandler(this);

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
        PostLoginDialog d = PostLoginDialog.showDialog(this);
    }
}
