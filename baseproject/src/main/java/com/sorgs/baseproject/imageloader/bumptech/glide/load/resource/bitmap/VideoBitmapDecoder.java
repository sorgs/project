package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.ParcelFileDescriptor;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.DecodeFormat;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.io.IOException;

/**
 * An {@link BitmapDecoder} that can decode a thumbnail frame
 * {@link Bitmap} from a {@link ParcelFileDescriptor} containing a video.
 *
 * @see MediaMetadataRetriever
 */
public class VideoBitmapDecoder implements BitmapDecoder<ParcelFileDescriptor> {
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();
    private static final int NO_FRAME = -1;
    private MediaMetadataRetrieverFactory factory;
    private int frame;

    public VideoBitmapDecoder() {
        this(DEFAULT_FACTORY, NO_FRAME);
    }

    VideoBitmapDecoder(MediaMetadataRetrieverFactory factory, int frame) {
        this.factory = factory;
        this.frame = frame;
    }

    public VideoBitmapDecoder(int frame) {
        this(DEFAULT_FACTORY, checkValidFrame(frame));
    }

    private static int checkValidFrame(int frame) {
        if (frame < 0) {
            throw new IllegalArgumentException("Requested frame must be non-negative");
        }
        return frame;
    }

    VideoBitmapDecoder(MediaMetadataRetrieverFactory factory) {
        this(factory, NO_FRAME);
    }

    @Override
    public Bitmap decode(ParcelFileDescriptor resource, BitmapPool bitmapPool, int outWidth, int outHeight,
                         DecodeFormat decodeFormat)
            throws IOException {
        MediaMetadataRetriever mediaMetadataRetriever = factory.build();
        mediaMetadataRetriever.setDataSource(resource.getFileDescriptor());
        Bitmap result;
        if (frame >= 0) {
            result = mediaMetadataRetriever.getFrameAtTime(frame);
        } else {
            result = mediaMetadataRetriever.getFrameAtTime();
        }
        mediaMetadataRetriever.release();
        resource.close();
        return result;
    }

    @Override
    public String getId() {
        return "VideoBitmapDecoder.com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap";
    }

    // Visible for testing.
    static class MediaMetadataRetrieverFactory {
        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }
}
