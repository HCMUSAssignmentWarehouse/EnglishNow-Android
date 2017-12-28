package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesManager;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseManager;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public interface AppDataSource extends FirebaseManager, PreferencesManager {
    void setUserAsLoggedOut();
}
