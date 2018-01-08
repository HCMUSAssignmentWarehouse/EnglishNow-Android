package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.api.ApiDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.MediaDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.NewsFeedItemDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.UserDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 23/12/2017.
 */

public interface DataManager extends ApiDataSource, FirebaseHelper, PreferencesHelper {
    UserDataSource getUserRepository();

    NewsFeedItemDataSource getNewsFeedItemRepository();

    MediaDataSource getMediaRepository();

    Observable<List<StatusItemData>> fetchAllStatusItemData();
}
