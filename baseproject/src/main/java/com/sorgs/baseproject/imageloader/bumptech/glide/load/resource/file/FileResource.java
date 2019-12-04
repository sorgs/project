package com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.file;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.resource.SimpleResource;

import java.io.File;

/**
 * A simple {@link com.sorgs.baseproject.imageloader.bumptech.glide.load.engine.Resource} that wraps a {@link File}.
 */
public class FileResource extends SimpleResource<File> {
    public FileResource(File file) {
        super(file);
    }
}
