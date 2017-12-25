package com.iceteaviet.englishnow;

import android.app.Application;
import android.content.Context;

import com.iceteaviet.englishnow.di.component.AppComponent;
import com.iceteaviet.englishnow.di.component.DaggerAppComponent;
import com.iceteaviet.englishnow.di.component.DaggerNetComponent;
import com.iceteaviet.englishnow.di.component.NetComponent;
import com.iceteaviet.englishnow.di.module.AppModule;
import com.iceteaviet.englishnow.di.module.NetModule;
import com.iceteaviet.englishnow.utils.AppLogger;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Genius Doan on 12/24/2017.
 */

// Dagger 2: We should do Instantiating the component within a specialization of the Application class
//            since these instances should be declared only once throughout the entire lifespan of the application
public class EnglishNowApp extends Application {
    private Scheduler scheduler;
    private AppComponent appComponent;
    private NetComponent netComponent;

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
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)) //TODO: Consider change to @BindsInstance
                .build();

        netComponent = DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.github.com"))
                .build();

        // If a Dagger 2 component does not have any constructor arguments for any of its modules,
        // then we can use .create() as a shortcut instead:
        //  netComponent = DaggerNetComponent.create();

        //Then use app component to injects in Activity/Service,...

        //AppLogger
        AppLogger.init();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
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
}
