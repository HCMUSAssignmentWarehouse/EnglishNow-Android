package com.iceteaviet.englishnow.data;

import com.iceteaviet.englishnow.data.local.prefs.PreferencesHelper;
import com.iceteaviet.englishnow.data.model.others.StatusItemData;
import com.iceteaviet.englishnow.data.remote.api.ApiHelper;
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

public interface DataManager extends ApiHelper, FirebaseHelper, PreferencesHelper {
    UserDataSource getUserRepository();

    NewsFeedItemDataSource getNewsFeedItemRepository();

    MediaDataSource getMediaRepository();

    VideoCallSessionDataSource getVideoCallSessionRepository();

    Observable<List<StatusItemData>> fetchAllStatusItemData();
}
