package com.iceteaviet.englishnow.ui.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by Genius Doan on 29/12/2017.
 * <p>
 * A provider factory pattern which responsible to instantiate multi-type of ViewModels.
 * Used if the ViewModels has a parameterize constructor.
 * <p>
 * Inside this factory, we could even use dependency injection to get everything we need.
 * move the responsibility of creating view models into a separate class which already made code better (single responsibility principle).
 */

public class ViewModelProviderFactory<V> implements ViewModelProvider.Factory {

    private V viewModel;

    public ViewModelProviderFactory(V viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModel.getClass())) {
            return (T) viewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }

}
