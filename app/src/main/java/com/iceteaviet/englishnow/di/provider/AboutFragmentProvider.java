package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.AboutModule;
import com.iceteaviet.englishnow.ui.about.view.AboutFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@Module
public abstract class AboutFragmentProvider {
    @ContributesAndroidInjector(modules = AboutModule.class)
    abstract AboutFragment provideAboutFragmentFactory();
}