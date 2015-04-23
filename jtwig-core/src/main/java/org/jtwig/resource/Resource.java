package org.jtwig.resource;

import com.google.common.base.Optional;

import org.jtwig.JtwigModel;

import java.io.InputStream;

public interface Resource {
    InputStream content();
    Optional<Resource> relative(String path);
}
