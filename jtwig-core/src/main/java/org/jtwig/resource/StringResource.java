package org.jtwig.resource;

import com.google.common.base.Optional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringResource implements Resource {
    private final String content;

    public StringResource(String content) {
        this.content = content;
    }

    @Override
    public InputStream content() {
        return new ByteArrayInputStream(content.getBytes());
    }

    @Override
    public Optional<Resource> relative(String path) {
        return Optional.absent();
    }
}
