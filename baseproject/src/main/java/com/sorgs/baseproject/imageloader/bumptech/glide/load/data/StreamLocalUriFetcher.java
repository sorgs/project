package com.sorgs.baseproject.imageloader.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Fetches an {@link InputStream} for a local {@link Uri}.
 */
public class StreamLocalUriFetcher extends LocalUriFetcher<InputStream> {
    public StreamLocalUriFetcher(Context context, Uri uri) {
        super(context, uri);
    }

    @Override
    protected void close(InputStream data) throws IOException {
        data.close();
    }

    @Override
    protected InputStream loadResource(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        return contentResolver.openInputStream(uri);
    }
}
