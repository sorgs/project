package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.gifbitmap;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.ResourceDecoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.Resource;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ImageVideoWrapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link ResourceDecoder} that can decode an
 * {@link GifBitmapWrapper} from {@link InputStream} data.
 */
public class GifBitmapWrapperStreamResourceDecoder implements ResourceDecoder<InputStream, GifBitmapWrapper> {
    private final ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> gifBitmapDecoder;

    public GifBitmapWrapperStreamResourceDecoder(
            ResourceDecoder<ImageVideoWrapper, GifBitmapWrapper> gifBitmapDecoder) {
        this.gifBitmapDecoder = gifBitmapDecoder;
    }

    @Override
    public Resource<GifBitmapWrapper> decode(InputStream source, int width, int height) throws IOException {
        return gifBitmapDecoder.decode(new ImageVideoWrapper(source, null), width, height);
    }

    @Override
    public String getId() {
        return gifBitmapDecoder.getId();
    }
}
