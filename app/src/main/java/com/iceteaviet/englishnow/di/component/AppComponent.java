package com.iceteaviet.englishnow.di.component;

import com.iceteaviet.englishnow.EnglishNowApp;
import com.iceteaviet.englishnow.di.module.AppModule;
import com.iceteaviet.englishnow.ui.splash.view.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Genius Doan on 12/24/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
//define what objects should be included as part of the dependency chain by modules
public interface AppComponent {
    void inject(EnglishNowApp app);
    // Note that the activities, services, or fragments that are allowed to request the dependencies declared by the modules
    // (by means of the @Inject annotation) should be declared in this class with individual inject() methods
    void inject(SplashActivity activity);
    // void inject(MyFragment fragment);
    // void inject(MyService service);
    //NOTE:
    // remove injection methods if downstream modules will perform injection
    // AND in that case:
    // downstream components need these exposed
    // the method name does not matter, only the return type
    // SharedPreferences getSharedPreferences();
}

//Note that Dagger 2 will generate Component builder for us automatically
// But also allows us to customize the generated builder by something knows as a Component.Builder
// Definition:
// Components may have a single nested static abstract class or interface annotated with @Component.Builder.
// If they do, then the component's generated builder will match the API in the type.
// @see: https://proandroiddev.com/dagger-2-component-builder-1f2b91237856
