package com.iceteaviet.englishnow.data.model.firebase;

import android.net.Uri;

/**
 * Created by Genius Doan on 06/01/2018.
 */

public class UploadTaskMessage {
    private double progress;
    private Uri result;

    public UploadTaskMessage(double progress, Uri result) {
        this.progress = progress;
        this.result = result;
    }

    public UploadTaskMessage(double progress) {
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }

    public Uri getResult() {
        return result;
    }
}
