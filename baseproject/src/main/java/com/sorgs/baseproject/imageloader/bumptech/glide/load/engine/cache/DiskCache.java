package com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.cache;


import com.sorgs.baseproject.imageloader.bumptech.glide.load.Key;

import java.io.File;

/**
 * An interface for writing to and reading from a disk cache.
 */
public interface DiskCache {

    /**
     * Get the cache for the value at the given key.
     *
     * <p>
     * Note - This is potentially dangerous, someone may write a new value to the file at any point in timeand we
     * won't know about it.
     * </p>
     *
     * @param key The key in the cache.
     * @return An InputStream representing the data at key at the time get is called.
     */
    File get(Key key);

    /**
     * Write to a key in the cache. {@link Writer} is used so that the cache implementation can perform actions after
     * the write finishes, like commit (via atomic file rename).
     *
     * @param key    The key to write to.
     * @param writer An interface that will write data given an OutputStream for the key.
     */
    void put(Key key, Writer writer);

    /**
     * Remove the key and value from the cache.
     *
     * @param key The key to remove.
     */
    void delete(Key key);

    /**
     * Clear the cache.
     */
    void clear();

    /**
     * An interface for lazily creating a disk cache.
     */
    interface Factory {

        /**
         * 250 MB of cache.
         */
        int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;
        String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";

        /**
         * Returns a new disk cache, or {@code null} if no disk cache could be created.
         */
        DiskCache build();

        File getCacheDir();
    }

    /**
     * An interface to actually write data to a key in the disk cache.
     */
    interface Writer {
        /**
         * Writes data to the file and returns true if the write was successful and should be committed, and
         * false if the write should be aborted.
         *
         * @param file The File the Writer should write to.
         */
        boolean write(File file);
    }
}
