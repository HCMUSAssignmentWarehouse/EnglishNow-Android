package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesManager;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseManager;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public interface AppDataSource extends FirebaseManager, PreferencesManager {
    void setUserAsLoggedOut();

    Observable<List<StatusItemData>> getAllStatusItems();
}
