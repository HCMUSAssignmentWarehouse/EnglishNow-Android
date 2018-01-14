package com.iceteaviet.englishnow.data.model.firebase.message;

import android.net.Uri;

/**
 * Created by Genius Doan on 06/01/2018.
 * <p>
 * Store information about a upload task response
 * Use it as a message for response information from data manager back to View-Model.
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
