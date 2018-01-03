package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.api.ApiDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public interface DataManager extends ApiDataSource, FirebaseDataSource, PreferencesHelper {
    void setUserAsLoggedOut();

    Observable<List<StatusItemData>> getAllStatusItems();
}
