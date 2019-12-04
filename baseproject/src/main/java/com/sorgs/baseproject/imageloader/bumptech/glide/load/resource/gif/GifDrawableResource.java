package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.gif;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.drawable.DrawableResource;
import com.sorgs.baseproject.imageloader.bumptech.glide.util.Util;

/**
 * A resource wrapping an {@link GifDrawable}.
 */
public class GifDrawableResource extends DrawableResource<GifDrawable> {
    public GifDrawableResource(GifDrawable drawable) {
        super(drawable);
    }

    @Override
    public int getSize() {
        return drawable.getData().length + Util.getBitmapByteSize(drawable.getFirstFrame());
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.recycle();
    }
}
