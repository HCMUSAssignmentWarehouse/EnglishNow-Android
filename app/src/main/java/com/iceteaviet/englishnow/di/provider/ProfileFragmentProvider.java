package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.ProfileModule;
import com.iceteaviet.englishnow.ui.profile.view.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 02/01/2018.
 */

@Module
public abstract class ProfileFragmentProvider {
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileFragment provideProfileFragmentFactory();
}