package org.jtwig.resource;

import org.jtwig.exceptions.JtwigException;

public class ResourceNotFoundException extends JtwigException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
