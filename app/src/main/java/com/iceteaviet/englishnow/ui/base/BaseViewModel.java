package com.iceteaviet.englishnow.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.iceteaviet.englishnow.data.AppDataRepository;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public abstract class BaseViewModel<H> extends ViewModel {
    private final AppDataRepository mAppRepository;
    private final SchedulerProvider mSchedulerProvider;
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private H mHandler;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(AppDataRepository repo,
                         SchedulerProvider schedulerProvider) {
        this.mAppRepository = repo;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public H getHandler() {
        return mHandler;
    }

    public void setHandler(H handler) {
        this.mHandler = handler;
    }

    public AppDataRepository getDataRepository() {
        return mAppRepository;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }
}
