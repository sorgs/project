package com.sorgs.baseproject.imageloader.bumptech.glide.load.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.sorgs.baseproject.imageloader.bumptech.glide.Priority;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A DataFetcher that uses an {@link ContentResolver} to load data from a {@link Uri}
 * pointing to a local resource.
 *
 * @param <T> The type of data that will obtained for the given uri (For example, {@link java.io.InputStream} or
 *            {@link android.os.ParcelFileDescriptor}.
 */
public abstract class LocalUriFetcher<T> implements DataFetcher<T> {
    private static final String TAG = "LocalUriFetcher";
    private final Uri uri;
    private final Context context;
    private T data;

    /**
     * Opens an input stream for a uri pointing to a local asset. Only certain uris are supported
     *
     * @param context A context (this will be weakly referenced and the load will fail if the weak reference
     *                is cleared before {@link #loadData(Priority)}} is called.
     * @param uri     A Uri pointing to a local asset. This load will fail if the uri isn't openable by
     *                {@link ContentResolver#openInputStream(Uri)}
     * @see ContentResolver#openInputStream(Uri)
     */
    public LocalUriFetcher(Context context, Uri uri) {
        this.context = context.getApplicationContext();
        this.uri = uri;
    }

    @Override
    public final T loadData(Priority priority) throws Exception {
        ContentResolver contentResolver = context.getContentResolver();
        data = loadResource(uri, contentResolver);
        return data;
    }

    @Override
    public void cleanup() {
        if (data != null) {
            try {
                close(data);
            } catch (IOException e) {
                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG, "failed to close data", e);
                }
            }

        }
    }

    @Override
    public String getId() {
        return uri.toString();
    }

    @Override
    public void cancel() {
        // Do nothing.
    }

    /**
     * Closes the concrete data type if necessary.
     *
     * <p>
     * Note - We can't rely on the closeable interface because it was added after our min API level. See issue #157.
     * </p>
     *
     * @param data The data to close.
     * @throws IOException
     */
    protected abstract void close(T data) throws IOException;

    /**
     * Returns a concrete data type from the given {@link Uri} using the given
     * {@link ContentResolver}.
     *
     * @throws FileNotFoundException
     */
    protected abstract T loadResource(Uri uri, ContentResolver contentResolver) throws FileNotFoundException;
}

