package org.jtwig.resource;

import com.google.common.base.Optional;

import java.net.URI;

public interface ResourceResolver {
    Optional<Resource> resolve(URI uri);
    Optional<Resource> resolve(Resource resource, String relativePath);
}
