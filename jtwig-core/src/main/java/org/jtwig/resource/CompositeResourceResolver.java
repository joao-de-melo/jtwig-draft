package org.jtwig.resource;

import com.google.common.base.Optional;

import java.net.URI;
import java.util.Collection;

public class CompositeResourceResolver implements ResourceResolver {
    private final Collection<ResourceResolver> resourceResolvers;

    public CompositeResourceResolver(Collection<ResourceResolver> resourceResolvers) {
        this.resourceResolvers = resourceResolvers;
    }

    @Override
    public Optional<Resource> resolve(URI uri) {
        for (ResourceResolver resourceResolver : resourceResolvers) {
            Optional<Resource> result = resourceResolver.resolve(uri);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.absent();
    }

    @Override
    public Optional<Resource> resolve(Resource resource, String relativePath) {
        for (ResourceResolver resourceResolver : resourceResolvers) {
            Optional<Resource> result = resourceResolver.resolve(resource, relativePath);
            if (result.isPresent()) {
                return result;
            }
        }

        return Optional.absent();
    }
}
