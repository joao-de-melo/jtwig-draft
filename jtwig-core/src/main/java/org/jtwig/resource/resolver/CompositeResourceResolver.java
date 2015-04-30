package org.jtwig.resource.resolver;

import com.google.common.base.Optional;
import org.jtwig.resource.Resource;

import java.util.Collection;

public class CompositeResourceResolver implements ResourceResolver {
    private final Collection<ResourceResolver> resourceResolvers;

    public CompositeResourceResolver(Collection<ResourceResolver> resourceResolvers) {
        this.resourceResolvers = resourceResolvers;
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

    public int size() {
        return resourceResolvers.size();
    }
}
