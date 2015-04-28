package org.jtwig.resource;

import org.jtwig.resource.classpath.ResourceLoader;
import org.jtwig.resource.exceptions.ResourceException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClasspathResource implements Resource {
    private final String path;
    private final ResourceLoader resourceLoader;

    public ClasspathResource(String path, ResourceLoader resourceLoader) {
        this.path = path;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public InputStream content() {
        return resourceLoader.load(path);
    }

    public String relativePath (String relative) {
        File parentFile = new File(path).getParentFile();
        File file = new File(parentFile, relative);
        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            throw new ResourceException(String.format("Unable to get canonical path for '%s'", file.getPath()), e);
        }
    }
}
