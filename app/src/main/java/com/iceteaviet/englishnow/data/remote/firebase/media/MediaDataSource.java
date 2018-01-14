package com.iceteaviet.englishnow.data.remote.firebase.media;

import android.net.Uri;

import com.iceteaviet.englishnow.data.model.firebase.message.UploadTaskMessage;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public interface MediaDataSource {
    Observable<UploadTaskMessage> putPhoto(Uri data);

    Observable<UploadTaskMessage> putVideo(Uri data);

    Observable<UploadTaskMessage> updatePhoto(Uri data);

    Observable<UploadTaskMessage> updateVideo(Uri data);

    void deletePhoto(String fileName);

    void deleteVideo(String fileName);
}
