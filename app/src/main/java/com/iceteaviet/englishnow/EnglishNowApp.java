package com.iceteaviet.englishnow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.iceteaviet.englishnow.di.component.DaggerAppComponent;
import com.iceteaviet.englishnow.utils.AppLogger;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Genius Doan on 12/24/2017.
 */

// Dagger 2: We should do Instantiating the component within a specialization of the Application class
//            since these instances should be declared only once throughout the entire lifespan of the application
public class EnglishNowApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    private Scheduler scheduler;

    private static EnglishNowApp get(Context context) {
        return (EnglishNowApp) context.getApplicationContext();
    }

    public static EnglishNowApp create(Context context) {
        return EnglishNowApp.get(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Application build component (which defines dependency graph)
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  netComponent = DaggerNetComponent.create();

        //Then use app component to injects in Activity/Service,...

        //AppLogger
        AppLogger.init();
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
