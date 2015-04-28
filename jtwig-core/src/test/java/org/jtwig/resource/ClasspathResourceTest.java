package org.jtwig.resource;

import org.jtwig.resource.classpath.ResourceLoader;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClasspathResourceTest {
    private ClasspathResource underTest;
    private ResourceLoader resourceLoader = mock(ResourceLoader.class);

    @Test
    public void relativeCanonicalPath() throws Exception {
        underTest = new ClasspathResource("/one/two/three", resourceLoader);

        String result = underTest.relativePath("../../path");

        assertThat(result, is("/path"));
    }
}