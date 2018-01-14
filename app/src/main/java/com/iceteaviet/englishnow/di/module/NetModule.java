package com.iceteaviet.englishnow.di.module;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iceteaviet.englishnow.data.remote.api.ApiEndPoint;
import com.iceteaviet.englishnow.di.OpenTokApiClient;
import com.iceteaviet.englishnow.di.OpenTokBaseUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Genius Doan on 11/01/2018.
 *
 * Provide dependencies about network stuffs
 */

@Module
public class NetModule {
    // Dagger will only look for methods annotated with @Provides
    @Provides
    @OpenTokBaseUrl
    String provideOpenTokBaseUrl() {
        return ApiEndPoint.OPENTOK_BASE_URL;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache);
        return client.build();
    }

    @Provides
    @Singleton
    @OpenTokApiClient
    Retrofit provideOpenTokRetrofit(Gson gson, OkHttpClient okHttpClient, @OpenTokBaseUrl String openTokBaseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(openTokBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}