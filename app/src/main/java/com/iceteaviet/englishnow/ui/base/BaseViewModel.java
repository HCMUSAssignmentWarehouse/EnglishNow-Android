package com.iceteaviet.englishnow.ui.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public abstract class BaseViewModel<H> extends ViewModel {
    private final AppDataSource appDataSource;
    private final SchedulerProvider schedulerProvider;
    private final ObservableBoolean isLoading = new ObservableBoolean(false);
    private H mHandler;
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(AppDataSource repo,
                         SchedulerProvider schedulerProvider) {
        this.appDataSource = repo;
        this.schedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public H getHandler() {
        return mHandler;
    }

    public void setHandler(H handler) {
        this.mHandler = handler;
    }

    public AppDataSource getAppDataSource() {
        return appDataSource;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }
}
