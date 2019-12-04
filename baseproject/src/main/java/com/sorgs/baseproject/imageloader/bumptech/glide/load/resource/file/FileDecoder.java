package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.file;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.ResourceDecoder;
import com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.Resource;

import java.io.File;

/**
 * A simple {@link ResourceDecoder} that creates resource for a given {@link File}.
 */
public class FileDecoder implements ResourceDecoder<File, File> {

    @Override
    public Resource<File> decode(File source, int width, int height) {
        return new FileResource(source);
    }

    @Override
    public String getId() {
        return "";
    }
}
