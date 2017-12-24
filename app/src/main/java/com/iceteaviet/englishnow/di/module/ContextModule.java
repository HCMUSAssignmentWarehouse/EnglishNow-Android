package com.iceteaviet.englishnow.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Genius Doan on 12/25/2017.
 */

@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}