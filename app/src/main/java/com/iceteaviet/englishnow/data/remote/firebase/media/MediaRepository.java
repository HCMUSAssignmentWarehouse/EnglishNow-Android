package com.iceteaviet.englishnow.data.remote.firebase.media;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteaviet.englishnow.data.model.firebase.UploadTaskMessage;
import com.iceteaviet.englishnow.utils.AppLogger;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by Genius Doan on 08/01/2018.
 */

public class MediaRepository implements MediaDataSource {
    private static final String TAG = MediaRepository.class.getSimpleName();
    private static final String STATUS_IMAGES = "status_images";
    private static final String STATUS_VIDEOS = "status_videos";

    private FirebaseStorage storage;

    @Inject
    public MediaRepository(FirebaseStorage storage) {
        this.storage = storage;
    }

    @Override
    public Observable<UploadTaskMessage> putPhoto(Uri data) {
        return Observable.create(e -> {
            StorageReference photoRef = storage.getReference(STATUS_IMAGES).child(data.getLastPathSegment());
            putFile(data, photoRef, e);
        });
    }

    @Override
    public Observable<UploadTaskMessage> putVideo(Uri data) {
        return Observable.create(e -> {
            StorageReference photoRef = storage.getReference(STATUS_VIDEOS).child(data.getLastPathSegment());
            putFile(data, photoRef, e);
        });
    }

    @Override
    public void deletePhoto(String fileName) {
        storage.getReference(STATUS_IMAGES)
                .child(fileName)
                .delete()
                .addOnFailureListener(e -> {
                    AppLogger.e(TAG, e.getMessage());
                    e.printStackTrace();
                });
    }

    @Override
    public void deleteVideo(String fileName) {
        storage.getReference(STATUS_VIDEOS)
                .child(fileName)
                .delete()
                .addOnFailureListener(e -> {
                    AppLogger.e(TAG, e.getMessage());
                    e.printStackTrace();
                });
    }

    private void putFile(Uri data, StorageReference reference, ObservableEmitter e) {
        UploadTask uploadTask = reference.putFile(data);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            if (progress < 100)
                e.onNext(new UploadTaskMessage(progress));
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            e.onError(exception);
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            e.onNext(new UploadTaskMessage(100, downloadUrl));
            e.onComplete();
        });
    }
}
