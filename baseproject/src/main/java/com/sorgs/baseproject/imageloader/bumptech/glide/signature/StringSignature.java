package com.sorgs.baseproject.imageloader.bumptech.glide.signature;

import com.sorgs.baseproject.imageloader.bumptech.glide.load.Key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * A unique Signature that wraps a String.
 */
public class StringSignature implements Key {
    private final String signature;

    public StringSignature(String signature) {
        if (signature == null) {
            throw new NullPointerException("Signature cannot be null!");
        }
        this.signature = signature;
    }

    @Override
    public int hashCode() {
        return signature.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StringSignature that = (StringSignature) o;

        return signature.equals(that.signature);
    }

    @Override
    public String toString() {
        return "StringSignature{"
                + "signature='" + signature + '\''
                + '}';
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        messageDigest.update(signature.getBytes(STRING_CHARSET_NAME));
    }
}
