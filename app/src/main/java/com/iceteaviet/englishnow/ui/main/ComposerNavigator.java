package com.iceteaviet.englishnow.ui.main;

import android.graphics.Bitmap;
import android.support.annotation.StringRes;
import android.text.SpannableStringBuilder;

import com.iceteaviet.englishnow.data.model.others.QueuedMedia;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public interface ComposerNavigator {
    void dismissDialog();

    void postStatus();

    void openPickChooseDialog();

    void handleError(@StringRes int stringId);

    void addMediaPreview(QueuedMedia item, Bitmap preview);

    void removeMediaPreview(QueuedMedia item);

    void appendToEditor(SpannableStringBuilder builder);

    void cancelFinishingUploadDialog();

    void updateFileUploadingProgress(int percent);

    void setDeletePhotoButtonEnabled(boolean enabled);
}
