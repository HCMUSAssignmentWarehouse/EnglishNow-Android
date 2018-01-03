package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.AboutModule;
import com.iceteaviet.englishnow.di.module.NewsFeedModule;
import com.iceteaviet.englishnow.di.module.ProfileModule;
import com.iceteaviet.englishnow.ui.about.view.AboutFragment;
import com.iceteaviet.englishnow.ui.newsfeed.view.NewsFeedFragment;
import com.iceteaviet.englishnow.ui.profile.view.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 03/01/2018.
 */

@Module
public abstract class MainFragmentProvider {
    @ContributesAndroidInjector(modules = NewsFeedModule.class)
    abstract NewsFeedFragment provideNewsFeedFragmentFactory();

    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment provideAboutFragmentFactory();

    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileFragment provideProfileFragmentFactory();
}
