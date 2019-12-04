package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import com.sorgs.baseproject.imageloader.bumptech.glide.GenericRequestBuilder;
import com.sorgs.baseproject.imageloader.bumptech.glide.Glide;
import com.sorgs.baseproject.imageloader.bumptech.glide.gifdecoder.GifDecoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.Encoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.Key;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.Transformation;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.NullEncoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.animation.GlideAnimation;
import com.sorgs.baseproject.imageloader.bumptech.glide.request.target.SimpleTarget;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

class GifFrameLoader {

    private final FrameCallback callback;
    private final GifDecoder gifDecoder;
    private final Handler handler;

    private boolean isRunning = false;
    private boolean isLoadPending = false;
    private GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> requestBuilder;
    private DelayTarget current;
    private boolean isCleared;

    public GifFrameLoader(Context context, FrameCallback callback, GifDecoder gifDecoder, int width, int height) {
        this(callback, gifDecoder, null,
                getRequestBuilder(context, gifDecoder, width, height, Glide.get(context).getBitmapPool()));
    }

    GifFrameLoader(FrameCallback callback, GifDecoder gifDecoder, Handler handler,
                   GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> requestBuilder) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper(), new FrameLoaderCallback());
        }
        this.callback = callback;
        this.gifDecoder = gifDecoder;
        this.handler = handler;
        this.requestBuilder = requestBuilder;
    }

    private static GenericRequestBuilder<GifDecoder, GifDecoder, Bitmap, Bitmap> getRequestBuilder(Context context,
                                                                                                   GifDecoder gifDecoder,
                                                                                                   int width,
                                                                                                   int height,
                                                                                                   BitmapPool bitmapPool) {
        GifFrameResourceDecoder frameResourceDecoder = new GifFrameResourceDecoder(bitmapPool);
        GifFrameModelLoader frameLoader = new GifFrameModelLoader();
        Encoder<GifDecoder> sourceEncoder = NullEncoder.get();
        return Glide.with(context)
                    .using(frameLoader, GifDecoder.class)
                    .load(gifDecoder)
                    .as(Bitmap.class)
                    .sourceEncoder(sourceEncoder)
                    .decoder(frameResourceDecoder)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(width, height);

    }

    @SuppressWarnings("unchecked")
    public void setFrameTransformation(Transformation<Bitmap> transformation) {
        if (transformation == null) {
            throw new NullPointerException("Transformation must not be null");
        }
        requestBuilder = requestBuilder.transform(transformation);
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        isCleared = false;

        loadNextFrame();
    }

    private void loadNextFrame() {
        if (!isRunning || isLoadPending) {
            return;
        }
        isLoadPending = true;

        gifDecoder.advance();
        long targetTime = SystemClock.uptimeMillis() + gifDecoder.getNextDelay();
        DelayTarget next = new DelayTarget(handler, gifDecoder.getCurrentFrameIndex(), targetTime);
        requestBuilder
                .signature(new FrameSignature())
                .into(next);
    }

    public void clear() {
        stop();
        if (current != null) {
            Glide.clear(current);
            current = null;
        }
        isCleared = true;
        // test.
    }

    public void stop() {
        isRunning = false;
    }

    public Bitmap getCurrentFrame() {
        return current != null ? current.getResource() : null;
    }

    // Visible for testing.
    void onFrameReady(DelayTarget delayTarget) {
        if (isCleared) {
            handler.obtainMessage(FrameLoaderCallback.MSG_CLEAR, delayTarget).sendToTarget();
            return;
        }

        DelayTarget previous = current;
        current = delayTarget;
        callback.onFrameReady(delayTarget.index);

        if (previous != null) {
            handler.obtainMessage(FrameLoaderCallback.MSG_CLEAR, previous).sendToTarget();
        }

        isLoadPending = false;
        loadNextFrame();
    }

    public interface FrameCallback {
        void onFrameReady(int index);
    }

    // Visible for testing.
    static class DelayTarget extends SimpleTarget<Bitmap> {
        private final Handler handler;
        private final int index;
        private final long targetTime;
        private Bitmap resource;

        public DelayTarget(Handler handler, int index, long targetTime) {
            this.handler = handler;
            this.index = index;
            this.targetTime = targetTime;
        }

        public Bitmap getResource() {
            return resource;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            this.resource = resource;
            Message msg = handler.obtainMessage(FrameLoaderCallback.MSG_DELAY, this);
            handler.sendMessageAtTime(msg, targetTime);
        }
    }

    // Visible for testing.
    static class FrameSignature implements Key {
        private final UUID uuid;

        public FrameSignature() {
            this(UUID.randomUUID());
        }

        // VisibleForTesting.
        FrameSignature(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public int hashCode() {
            return uuid.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof FrameSignature) {
                FrameSignature other = (FrameSignature) o;
                return other.uuid.equals(uuid);
            }
            return false;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    private class FrameLoaderCallback implements Handler.Callback {
        public static final int MSG_DELAY = 1;
        public static final int MSG_CLEAR = 2;

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MSG_DELAY) {
                DelayTarget target = (DelayTarget) msg.obj;
                onFrameReady(target);
                return true;
            } else if (msg.what == MSG_CLEAR) {
                DelayTarget target = (DelayTarget) msg.obj;
                Glide.clear(target);
            }
            return false;
        }
    }
}
