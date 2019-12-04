package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;

import com.sorgs.baseproject.imageloader.bumptech.glide.Glide;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.DecodeFormat;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.ResourceDecoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.Resource;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.io.InputStream;

/**
 * An {@link ResourceDecoder} that uses an
 * {@link Downsampler} to decode an {@link Bitmap} from an
 * {@link InputStream}.
 */
public class StreamBitmapDecoder implements ResourceDecoder<InputStream, Bitmap> {
    private static final String ID = "StreamBitmapDecoder.com.sorgs.baseproject.imageloader.bumptech.glide.load" +
            ".resource.bitmap";
    private final Downsampler downsampler;
    private BitmapPool bitmapPool;
    private DecodeFormat decodeFormat;
    private String id;

    public StreamBitmapDecoder(Context context) {
        this(Glide.get(context).getBitmapPool());
    }

    public StreamBitmapDecoder(BitmapPool bitmapPool) {
        this(bitmapPool, DecodeFormat.DEFAULT);
    }

    public StreamBitmapDecoder(BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this(Downsampler.AT_LEAST, bitmapPool, decodeFormat);
    }

    public StreamBitmapDecoder(Downsampler downsampler, BitmapPool bitmapPool, DecodeFormat decodeFormat) {
        this.downsampler = downsampler;
        this.bitmapPool = bitmapPool;
        this.decodeFormat = decodeFormat;
    }

    public StreamBitmapDecoder(Context context, DecodeFormat decodeFormat) {
        this(Glide.get(context).getBitmapPool(), decodeFormat);
    }

    @Override
    public Resource<Bitmap> decode(InputStream source, int width, int height) {
        Bitmap bitmap = downsampler.decode(source, bitmapPool, width, height, decodeFormat);
        return BitmapResource.obtain(bitmap, bitmapPool);
    }

    @Override
    public String getId() {
        if (id == null) {
            id = new StringBuilder()
                    .append(ID)
                    .append(downsampler.getId())
                    .append(decodeFormat.name())
                    .toString();
        }
        return id;
    }
}
