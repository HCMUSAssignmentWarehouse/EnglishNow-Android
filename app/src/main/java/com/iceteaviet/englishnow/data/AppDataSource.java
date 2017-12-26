package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.remote.FirebaseHelper;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public interface AppDataSource extends FirebaseHelper {
    void setUserAsLoggedOut();
}
