package com.iceteaviet.englishnow.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Genius Doan on 11/01/2018.
 *
 * If we need two different objects of the same return type, we can use the Qualifier annotation.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenTokBaseUrl {
}

