package com.sorgs.baseproject.imageloader.bumptech.glide.load.engine;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.Key;

interface EngineJobListener {

    void onEngineJobComplete(Key key, EngineResource<?> resource);

    void onEngineJobCancelled(EngineJob engineJob, Key key);
}
