package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.auth.LoginModule;
import com.iceteaviet.englishnow.di.module.intro.IntroModule;
import com.iceteaviet.englishnow.di.module.main.MainModule;
import com.iceteaviet.englishnow.di.module.matching.ConversationMatchingModule;
import com.iceteaviet.englishnow.di.module.splash.SplashModule;
import com.iceteaviet.englishnow.ui.auth.view.LoginActivity;
import com.iceteaviet.englishnow.ui.intro.view.IntroActivity;
import com.iceteaviet.englishnow.ui.main.view.MainActivity;
import com.iceteaviet.englishnow.ui.matching.view.ConversationMatchingActivity;
import com.iceteaviet.englishnow.ui.splash.view.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 12/27/2017.
 */

@Module
public abstract class ActivityProvider {
    @ContributesAndroidInjector(modules = {LoginModule.class, AuthFragmentProvider.class, PostLoginProvider.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {SplashModule.class, PostLoginProvider.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = IntroModule.class)
    abstract IntroActivity bindIntroActivity();

    @ContributesAndroidInjector(modules = ConversationMatchingModule.class)
    abstract ConversationMatchingActivity bindConversationMatchingActivity();

    @ContributesAndroidInjector(modules = {MainModule.class, MainFragmentProvider.class})
    abstract MainActivity bindMainActivity();
}
