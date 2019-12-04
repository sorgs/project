package com.sorgs.baseproject.imageloader.bumptech.glide.load.model.stream;

import android.content.Context;
import android.net.Uri;

import com.sorgs.baseproject.imageloader.bumptech.glide.Glide;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.GenericLoaderFactory;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ModelLoader;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ModelLoaderFactory;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.StringLoader;

import java.io.InputStream;

/**
 * A {@link ModelLoader} for translating {@link String} models, such as file paths or remote urls, into
 * {@link InputStream} data.
 */
public class StreamStringLoader extends StringLoader<InputStream> implements StreamModelLoader<String> {

    public StreamStringLoader(Context context) {
        this(Glide.buildStreamModelLoader(Uri.class, context));
    }

    public StreamStringLoader(ModelLoader<Uri, InputStream> uriLoader) {
        super(uriLoader);
    }

    /**
     * The default factory for {@link StreamStringLoader}s.
     */
    public static class Factory implements ModelLoaderFactory<String, InputStream> {
        @Override
        public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new StreamStringLoader(factories.buildModelLoader(Uri.class, InputStream.class));
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }
}
