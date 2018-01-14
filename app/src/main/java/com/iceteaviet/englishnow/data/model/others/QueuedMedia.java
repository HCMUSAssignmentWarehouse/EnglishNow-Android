package com.iceteaviet.englishnow.data.model.others;

import android.net.Uri;
import android.text.style.URLSpan;

import com.iceteaviet.englishnow.data.model.firebase.message.UploadTaskMessage;
import com.iceteaviet.englishnow.ui.custom.ProgressImageView;

import java.io.Serializable;

import io.reactivex.Observable;

/**
 * Created by Genius Doan on 05/01/2018.
 * <p>
 * Model for storing information about queued media to upload
 */

public final class QueuedMedia implements Serializable {
    private Type type;
    private ProgressImageView preview;
    private Uri localUri;
    private String id;
    private Observable<UploadTaskMessage> uploadRequest;
    private URLSpan uploadUrl;
    private ReadyStage readyStage;
    private byte[] content;
    private long mediaSize;

    public QueuedMedia(Type type, Uri localUri, long mediaSize) {
        this.type = type;
        this.localUri = localUri;
        this.mediaSize = mediaSize;
    }

    public QueuedMedia(Type type, Uri localUri, ProgressImageView preview, long mediaSize) {
        this.type = type;
        this.localUri = localUri;
        this.preview = preview;
        this.mediaSize = mediaSize;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ProgressImageView getPreview() {
        return preview;
    }

    public void setPreview(ProgressImageView preview) {
        this.preview = preview;
    }

    public Uri getLocalUri() {
        return localUri;
    }

    public void setLocalUri(Uri localUri) {
        this.localUri = localUri;
    }

    @Deprecated
    public String getId() {
        return id;
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

    public Observable<UploadTaskMessage> getUploadRequest() {
        return uploadRequest;
    }

    public void setUploadRequest(Observable<UploadTaskMessage> uploadRequest) {
        this.uploadRequest = uploadRequest;
    }

    public URLSpan getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(URLSpan uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public ReadyStage getReadyStage() {
        return readyStage;
    }

    public void setReadyStage(ReadyStage readyStage) {
        this.readyStage = readyStage;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(long mediaSize) {
        this.mediaSize = mediaSize;
    }

    public enum Type {
        IMAGE,
        VIDEO
    }

    public enum ReadyStage {
        DOWNSIZING,
        UPLOADING,
        UPLOADED
    }
}