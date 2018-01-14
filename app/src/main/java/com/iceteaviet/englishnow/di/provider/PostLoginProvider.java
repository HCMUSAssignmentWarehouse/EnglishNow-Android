package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.PostLoginModule;
import com.iceteaviet.englishnow.ui.others.view.PostLoginDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 28/12/2017.
 *
 * Generate dependencies for PostLoginDialog that used inside Login/Splash activity
 */

@Module
public abstract class PostLoginProvider {
    @ContributesAndroidInjector(modules = PostLoginModule.class)
    abstract PostLoginDialog providePostLoginDialogFragment();
}
