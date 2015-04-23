package org.jtwig.resource;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompositeResourceResolverTest {
    private Collection<ResourceResolver> resolvers = new ArrayList<>();
    private CompositeResourceResolver underTest = new CompositeResourceResolver(resolvers);

    @Before
    public void setUp() throws Exception {
        resolvers.clear();
    }

    @Test
    public void resolveUriWhenNoneResolves() throws Exception {
        URI uri = URI.create("/one");
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(uri)).thenReturn(Optional.<Resource>absent());

        Optional<Resource> result = underTest.resolve(uri);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveUriWhenSomeResolves() throws Exception {
        URI uri = URI.create("/one");
        Resource reference = mock(Resource.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(uri)).thenReturn(Optional.of(reference));

        Optional<Resource> result = underTest.resolve(uri);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(reference));
    }

    @Test
    public void resolveResourceWhenNoneResolves() throws Exception {
        Resource resource = mock(Resource.class);
        String relativePath = "/ba";
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(resource, relativePath)).thenReturn(Optional.<Resource>absent());

        Optional<Resource> result = underTest.resolve(resource, relativePath);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveResourceWhenSomeResolves() throws Exception {
        Resource resource = mock(Resource.class);
        String relativePath = "/ba";
        Resource reference = mock(Resource.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        resolvers.add(resourceResolver);
        when(resourceResolver.resolve(resource, relativePath)).thenReturn(Optional.of(reference));

        Optional<Resource> result = underTest.resolve(resource, relativePath);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(reference));
    }
}