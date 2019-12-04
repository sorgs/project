package com.sorgs.baseproject.imageloader.bumptech.glide;

import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ImageVideoModelLoader;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ImageVideoWrapper;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.model.ModelLoader;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.provider.DataLoadProvider;
import com.sorgs.baseproject.imageloader.bumptech.glide.provider.FixedLoadProvider;

import java.io.InputStream;

/**
 * A class for creating a load request that either loads an {@link Bitmap} directly or that adds an
 * {@link ResourceTranscoder} to transcode the {@link Bitmap} into another
 * resource type.
 *
 * @param <ModelType> The type of model to load the {@link Bitmap} or transcoded class from.
 */
public class BitmapTypeRequest<ModelType> extends BitmapRequestBuilder<ModelType, Bitmap> {
    private final ModelLoader<ModelType, InputStream> streamModelLoader;
    private final ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader;
    private final Glide glide;
    private final RequestManager.OptionsApplier optionsApplier;

    BitmapTypeRequest(GenericRequestBuilder<ModelType, ?, ?, ?> other,
                      ModelLoader<ModelType, InputStream> streamModelLoader,
                      ModelLoader<ModelType, ParcelFileDescriptor> fileDescriptorModelLoader,
                      RequestManager.OptionsApplier optionsApplier) {
        super(buildProvider(other.glide, streamModelLoader, fileDescriptorModelLoader, Bitmap.class, null),
                Bitmap.class, other);
        this.streamModelLoader = streamModelLoader;
        this.fileDescriptorModelLoader = fileDescriptorModelLoader;
        this.glide = other.glide;
        this.optionsApplier = optionsApplier;
    }

    private static <A, R> FixedLoadProvider<A, ImageVideoWrapper, Bitmap, R> buildProvider(Glide glide,
                                                                                           ModelLoader<A,
                                                                                                   InputStream> streamModelLoader,
                                                                                           ModelLoader<A,
                                                                                                   ParcelFileDescriptor> fileDescriptorModelLoader,
                                                                                           Class<R> transcodedClass,
                                                                                           ResourceTranscoder<Bitmap,
                                                                                                   R> transcoder) {
        if (streamModelLoader == null && fileDescriptorModelLoader == null) {
            return null;
        }

        if (transcoder == null) {
            transcoder = glide.buildTranscoder(Bitmap.class, transcodedClass);
        }
        DataLoadProvider<ImageVideoWrapper, Bitmap> loadProvider = glide.buildDataProvider(ImageVideoWrapper.class,
                Bitmap.class);
        ImageVideoModelLoader<A> modelLoader = new ImageVideoModelLoader<A>(streamModelLoader,
                fileDescriptorModelLoader);

        return new FixedLoadProvider<A, ImageVideoWrapper, Bitmap, R>(modelLoader, transcoder, loadProvider);
    }

    /**
     * Transcodes the decoded and transformed {@link Bitmap} to bytes by compressing it as a JPEG to a byte array.
     * array.
     *
     * @return This request builder.
     *
     * @see #toBytes(Bitmap.CompressFormat, int)
     */
    public BitmapRequestBuilder<ModelType, byte[]> toBytes() {
        return transcode(new BitmapBytesTranscoder(), byte[].class);
    }

    /**
     * Sets a transcoder to transcode the decoded and transformed {@link Bitmap} into another resource type.
     *
     * @param transcoder     The transoder to use.
     * @param transcodeClass The {@link Class} of the resource the {@link Bitmap} will be transcoded to.
     * @param <R>            The type of the resource the {@link Bitmap} will be transcoded to.
     * @return This request builder.
     */
    public <R> BitmapRequestBuilder<ModelType, R> transcode(ResourceTranscoder<Bitmap, R> transcoder,
                                                            Class<R> transcodeClass) {
        return optionsApplier.apply(new BitmapRequestBuilder<ModelType, R>(
                buildProvider(glide, streamModelLoader, fileDescriptorModelLoader, transcodeClass, transcoder),
                transcodeClass, this));
    }

    /**
     * Transcodes the decoded and transformed {@link Bitmap} to bytes by compressing it using the
     * given format and quality to a byte array.
     *
     * @param compressFormat The {@link Bitmap.CompressFormat} to use to compress the {@link Bitmap}.
     * @param quality        The quality level from 0-100 to use to compress the {@link Bitmap}.
     * @return This request builder.
     *
     * @see Bitmap#compress(Bitmap.CompressFormat, int, java.io.OutputStream)
     * @see #toBytes()
     */
    public BitmapRequestBuilder<ModelType, byte[]> toBytes(Bitmap.CompressFormat compressFormat, int quality) {
        return transcode(new BitmapBytesTranscoder(compressFormat, quality), byte[].class);
    }
}
