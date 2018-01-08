package com.iceteaviet.englishnow.ui.main.viewmodel;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Px;
import android.text.style.URLSpan;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.iceteaviet.englishnow.R;
import com.iceteaviet.englishnow.data.DataManager;
import com.iceteaviet.englishnow.data.model.firebase.Status;
import com.iceteaviet.englishnow.data.model.firebase.UploadTaskMessage;
import com.iceteaviet.englishnow.data.model.others.QueuedMedia;
import com.iceteaviet.englishnow.ui.base.BaseViewModel;
import com.iceteaviet.englishnow.ui.main.ComposerNavigator;
import com.iceteaviet.englishnow.utils.AppLogger;
import com.iceteaviet.englishnow.utils.CommonUtils;
import com.iceteaviet.englishnow.utils.CountUpDownLatch;
import com.iceteaviet.englishnow.utils.FileUtils;
import com.iceteaviet.englishnow.utils.StringUtils;
import com.iceteaviet.englishnow.utils.rx.SchedulerProvider;
import com.iceteaviet.englishnow.utils.ui.DownsizeImageTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.functions.Consumer;

/**
 * Created by Genius Doan on 04/01/2018.
 */

public class ComposerViewModel extends BaseViewModel<ComposerNavigator> {
    public static final int MEDIA_PICK_RESULT = 1;
    public static final int MEDIA_TAKE_PHOTO_RESULT = 2;
    private static final String TAG = "ComposerViewModel";
    @Px
    private static final int THUMBNAIL_SIZE = 128;
    private static final int STATUS_CHARACTER_LIMIT = 140;
    private static final int STATUS_MEDIA_SIZE_LIMIT = 8388608; // 8MiB


    private ContentResolver contentResolver;
    private QueuedMedia queuedMedia;
    private Context context;


    public ComposerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onPostButtonClicked() {
        getNavigator().postStatus();
    }

    public void onPickButtonClicked() {
        getNavigator().openPickChooseDialog();
    }

    public void onToggleVisibilityButtonClicked() {

    }

    public void onSaveDraftButtonClicked() {

    }

    public void onCloseButtonClicked() {
        getNavigator().dismissDialog();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @NonNull
    public File createNewImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "EnglishNow_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    public void pickMedia(Uri uri) {
        long mediaSize = FileUtils.getMediaSize(contentResolver, uri);

        if (mediaSize == FileUtils.MEDIA_SIZE_UNKNOWN) {
            getNavigator().displayTransientError(R.string.error_media_upload_opening);
            return;
        }

        String mimeType = contentResolver.getType(uri);
        if (mimeType != null) {
            String topLevelType = mimeType.substring(0, mimeType.indexOf('/'));
            switch (topLevelType) {
                case "video": {
                    if (mediaSize > STATUS_MEDIA_SIZE_LIMIT) {
                        getNavigator().displayTransientError(R.string.error_media_upload_size);
                        return;
                    }
                    if (queuedMedia != null
                            && queuedMedia.getType() == QueuedMedia.Type.IMAGE) {
                        getNavigator().displayTransientError(R.string.error_media_upload_image_or_video);
                        return;
                    }
                    Bitmap bitmap = FileUtils.getVideoThumbnail(context, uri, THUMBNAIL_SIZE);
                    if (bitmap != null) {
                        addMediaToQueue(QueuedMedia.Type.VIDEO, bitmap, uri, mediaSize, null);
                    } else {
                        getNavigator().displayTransientError(R.string.error_media_upload_opening);
                    }
                    break;
                }
                case "image": {
                    Bitmap bitmap = FileUtils.getImageThumbnail(contentResolver, uri, THUMBNAIL_SIZE);
                    if (bitmap != null) {
                        addMediaToQueue(QueuedMedia.Type.IMAGE, bitmap, uri, mediaSize, null);
                    } else {
                        getNavigator().displayTransientError(R.string.error_media_upload_opening);
                    }
                    break;
                }
                default: {
                    getNavigator().displayTransientError(R.string.error_media_upload_type);
                    break;
                }
            }
        } else {
            getNavigator().displayTransientError(R.string.error_media_upload_type);
        }
    }

    private void addMediaToQueue(QueuedMedia.Type type, Bitmap preview, Uri uri, long mediaSize, QueuedMedia.ReadyStage readyStage) {
        final QueuedMedia item = new QueuedMedia(type, uri, mediaSize);
        item.setReadyStage(readyStage);

        queuedMedia = item;

        getNavigator().addMediaPreview(item, preview);

        if (item.getReadyStage() != QueuedMedia.ReadyStage.UPLOADED) {
            CountUpDownLatch.getInstance().countUp();
            if (mediaSize > STATUS_MEDIA_SIZE_LIMIT && type == QueuedMedia.Type.IMAGE) {
                downsizeMedia(item);
            } else {
                uploadMedia(item);
            }
        }
    }

    private void downsizeMedia(final QueuedMedia item) {
        item.setReadyStage(QueuedMedia.ReadyStage.DOWNSIZING);

        new DownsizeImageTask(STATUS_MEDIA_SIZE_LIMIT, contentResolver,
                new DownsizeImageTask.Listener() {
                    @Override
                    public void onSuccess(List<byte[]> contentList) {
                        item.setContent(contentList.get(0));
                        uploadMedia(item);
                    }

                    @Override
                    public void onFailure() {
                        onMediaDownsizeFailure(item);
                    }
                }).execute(item.getLocalUri());
    }

    public void uploadMedia(final QueuedMedia item) {
        item.setReadyStage(QueuedMedia.ReadyStage.UPLOADING);

        String mimeType = contentResolver.getType(item.getLocalUri());
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String fileExtension = map.getExtensionFromMimeType(mimeType);
        final String filename = String.format("%s_%s_%s.%s",
                context.getString(R.string.app_name),
                String.valueOf(new Date().getTime()),
                CommonUtils.randomAlphanumericString(10),
                fileExtension);

        byte[] content = item.getContent();

        if (content == null) {
            InputStream stream;

            try {
                stream = contentResolver.openInputStream(item.getLocalUri());
            } catch (FileNotFoundException e) {
                AppLogger.d(e.getMessage());
                return;
            }

            content = FileUtils.inputStreamGetBytes(stream);
            FileUtils.closeQuietly(stream);

            if (content == null) {
                return;
            }
        }

        item.setUploadRequest(getDataManager().getMediaRepository().putPhoto(item.getLocalUri()));
        getCompositeDisposable().add(
                item.getUploadRequest()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<UploadTaskMessage>() {
                            int lastProgress = -1;

                            @Override
                            public void accept(UploadTaskMessage uploadTaskMessage) throws Exception {
                                int progress = (int) Math.round(uploadTaskMessage.getProgress());
                                if (progress == lastProgress)
                                    return;

                                item.getPreview().setProgress(progress);
                                if (progress == 100)
                                    onUploadSuccess(item, uploadTaskMessage.getResult());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                AppLogger.d("Upload request failed. " + throwable.getMessage());
                                onUploadFailure(item, throwable);
                            }
                        })
        );
    }

    private void onMediaDownsizeFailure(QueuedMedia item) {
        getNavigator().displayTransientError(R.string.error_media_upload_size);
        removeMediaFromQueue(item);
    }

    public void removeMediaFromQueue(QueuedMedia item) {
        queuedMedia = null;
        getNavigator().removeMediaPreview(item);
        cancelReadyingMedia(item);
    }

    private void cancelReadyingMedia(QueuedMedia item) {
        if (item.getReadyStage() == QueuedMedia.ReadyStage.UPLOADING) {
            //item.getUploadRequest().cancel(); //TODO: Support cancel
        }
        if (item.getId() == null) {
            /* The presence of an upload id is used to detect if it finished uploading or not, to
             * prevent counting down twice on the same media item. */
            CountUpDownLatch.getInstance().countDown();
        } else {
            String decodedUrl = StringUtils.decodeUrl(item.getUploadUrl().getURL());
            String fileName = StringUtils.getFileNameFromPath(decodedUrl);
            getDataManager().getMediaRepository().deletePhoto(fileName);
        }
    }

    private void onUploadSuccess(final QueuedMedia item, Uri media) {
        if (media == null)
            return;
        item.setId(media.getPath());
        item.getPreview().setProgress(-1);
        item.setReadyStage(QueuedMedia.ReadyStage.UPLOADED);

        /* Add the upload URL to the text field. Also, keep a reference to the span so if the user
         * chooses to remove the media, the URL is also automatically removed. */
        item.setUploadUrl(new URLSpan(media.toString()));

        /*
        int end = 1 + media.getPath().length();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(' ');
        builder.append(media.getPath());
        builder.setSpan(item.getUploadUrl(), 1, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        getNavigator().appendToEditor(builder);
        */

        getNavigator().setDeletePhotoButtonEnabled(true);

        CountUpDownLatch.getInstance().countDown();
    }

    private void onUploadFailure(QueuedMedia item, Throwable throwable) {
        if (!throwable.getMessage().contains("cancel")) { //TODO: Support
            /* if the upload was voluntarily cancelled, such as if the user clicked on it to remove
             * it from the queue, then don't display this error message. */
            getNavigator().displayTransientError(R.string.error_media_upload_sending);
        }

        getNavigator().cancelFinishingUploadDialog();

        if (!throwable.getMessage().contains("cancel")) {
            // If it is canceled, it's already been removed, otherwise do it.
            removeMediaFromQueue(item);
        }
    }

    public boolean isStatusValid(String content) {
        return content.length() > 0;
    }

    public void doPostStatus(String content) {
        String uid = getDataManager().getCurrentUserUid();
        String userDisplayName = getDataManager().getCurrentUserDisplayName();

        if (uid == null || userDisplayName == null) {
            Toast.makeText(context, R.string.user_auth_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        Status.Builder builder = new Status.Builder()
                .setOwnerUid(uid)
                .setOwnerUsername(userDisplayName)
                .setContent(content)
                .setTimestamp(System.currentTimeMillis());

        if (queuedMedia != null) {
            builder = builder.setPhotoUrl(queuedMedia.getUploadUrl().getURL());
        }

        Status status = builder.build();
        getDataManager().getNewsFeedItemRepository().createOrUpdate(status);
    }
}
