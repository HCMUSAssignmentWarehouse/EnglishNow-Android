package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.about.AboutModule;
import com.iceteaviet.englishnow.di.module.main.ComposerModule;
import com.iceteaviet.englishnow.di.module.newsfeed.NewsFeedModule;
import com.iceteaviet.englishnow.di.module.newsfeed.StatusModule;
import com.iceteaviet.englishnow.di.module.newsfeed.VideoStatusModule;
import com.iceteaviet.englishnow.di.module.profile.ProfileModule;
import com.iceteaviet.englishnow.ui.about.view.AboutFragment;
import com.iceteaviet.englishnow.ui.main.view.StatusComposerDialog;
import com.iceteaviet.englishnow.ui.newsfeed.status.view.StatusFragment;
import com.iceteaviet.englishnow.ui.newsfeed.videostatus.view.VideoStatusFragment;
import com.iceteaviet.englishnow.ui.newsfeed.view.NewsFeedFragment;
import com.iceteaviet.englishnow.ui.profile.view.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 03/01/2018.
 * <p>
 * Generate dependencies for sub-component of MainActivity (Fragments, DialogFragments,..)
 */

@Module
public abstract class MainFragmentProvider {
    @ContributesAndroidInjector(modules = {NewsFeedModule.class})
    abstract NewsFeedFragment provideNewsFeedFragment();

    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment provideAboutFragment();

    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileFragment provideProfileFragment();

    @ContributesAndroidInjector(modules = ComposerModule.class)
    abstract StatusComposerDialog provideComposerDialogFragment();

    @ContributesAndroidInjector(modules = StatusModule.class)
    abstract StatusFragment provideStatusFragment();

    @ContributesAndroidInjector(modules = VideoStatusModule.class)
    abstract VideoStatusFragment provideVideoStatusFragment();
}
