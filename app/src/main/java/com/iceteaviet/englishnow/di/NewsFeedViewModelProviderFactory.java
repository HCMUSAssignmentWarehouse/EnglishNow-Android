package com.iceteaviet.englishnow.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Genius Doan on 11/01/2018.
 * <p>
 * If we need two different objects of the same return type, we can use the Qualifier annotation.
 * <p>
 * This is define the dependency type for Retrofit client that used for executing OpenTok services
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface NewsFeedViewModelProviderFactory {
}

