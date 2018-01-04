package com.iceteaviet.englishnow.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Genius Doan on 05/01/2018.
 */

public final class FileUtils {
    public static final int MEDIA_SIZE_UNKNOWN = -1;

    private FileUtils() {

    }

    /**
     * Copies the entire contents of the given stream into a byte array and returns it. Beware of
     * OutOfMemoryError for streams of unknown size.
     */
    @Nullable
    public static byte[] inputStreamGetBytes(InputStream stream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[16384];
        try {
            while ((read = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
            buffer.flush();
        } catch (IOException e) {
            return null;
        }
        return buffer.toByteArray();
    }

    /**
     * Fetches the size of the media represented by the given URI, assuming it is openable and
     * the ContentResolver is able to resolve it.
     *
     * @return the size of the media or {@link FileUtils#MEDIA_SIZE_UNKNOWN}
     */
    public static long getMediaSize(@NonNull ContentResolver contentResolver, @Nullable Uri uri) {
        if (uri == null) return MEDIA_SIZE_UNKNOWN;
        long mediaSize;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            mediaSize = cursor.getLong(sizeIndex);
            cursor.close();
        } else {
            mediaSize = MEDIA_SIZE_UNKNOWN;
        }
        return mediaSize;
    }

    @Nullable
    public static Bitmap getImageThumbnail(ContentResolver contentResolver, Uri uri,
                                           @Px int thumbnailSize) {
        InputStream stream;
        try {
            stream = contentResolver.openInputStream(uri);
        } catch (FileNotFoundException e) {
            return null;
        }
        Bitmap source = BitmapFactory.decodeStream(stream);
        if (source == null) {
            closeQuietly(stream);
            return null;
        }
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, thumbnailSize, thumbnailSize);
        source.recycle();
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            bitmap.recycle();
            return null;
        }
        return bitmap;
    }

    @Nullable
    public static Bitmap getVideoThumbnail(Context context, Uri uri, @Px int thumbnailSize) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, uri);
        Bitmap source = retriever.getFrameAtTime();
        if (source == null) {
            return null;
        }
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, thumbnailSize, thumbnailSize);
        source.recycle();
        return bitmap;
    }

    public static void closeQuietly(@Nullable InputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // intentionally unhandled
        }
    }

    public static void closeQuietly(@Nullable OutputStream stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            // intentionally unhandled
        }
    }
}
