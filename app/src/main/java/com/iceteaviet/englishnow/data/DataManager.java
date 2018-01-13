package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.api.ApiDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.FirebaseHelper;
import com.iceteaviet.englishnow.data.remote.firebase.media.MediaDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.newsfeed.NewsFeedItemDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.user.UserDataSource;
import com.iceteaviet.englishnow.data.remote.firebase.videocall.VideoCallSessionDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 23/12/2017.
 */

//TODO: Turn it into Facade Pattern
public interface DataManager extends ApiDataSource, FirebaseHelper, PreferencesHelper {
    UserDataSource getUserRepository();

    NewsFeedItemDataSource getNewsFeedItemRepository();

    MediaDataSource getMediaRepository();

    VideoCallSessionDataSource getVideoCallSessionRepository();

    Observable<List<StatusItemData>> fetchAllStatusItemData();
}
