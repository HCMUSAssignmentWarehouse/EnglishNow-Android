package com.iceteaviet.englishnow.di.provider;

import com.iceteaviet.englishnow.di.module.auth.AuthDialogModule;
import com.iceteaviet.englishnow.ui.auth.view.RegisterDialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Genius Doan on 28/12/2017.
 */

@Module
public abstract class AuthFragmentProvider {
    @ContributesAndroidInjector(modules = AuthDialogModule.class)
    abstract RegisterDialog provideRegisterDialogFactory();
}
