package com.iceteaviet.englishnow.di.builder;

import com.iceteaviet.englishnow.di.module.LoginModule;
import com.iceteaviet.englishnow.di.module.SplashModule;
import com.iceteaviet.englishnow.ui.login.view.LoginActivity;
import com.iceteaviet.englishnow.ui.splash.view.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity bindSplashActivity();
}
