package com.iceteaviet.englishnow.ui.profile.viewmodel;

import com.iceteaviet.englishnow.data.AppDataSource;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.profile.ProfileHandler;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;

/**
 * Created by Genius Doan on 02/01/2018.
 */

public class ProfileViewModel extends BaseViewModel<ProfileHandler> {
    public ProfileViewModel(AppDataSource repo, SchedulerProvider schedulerProvider) {
        super(repo, schedulerProvider);
    }
}
