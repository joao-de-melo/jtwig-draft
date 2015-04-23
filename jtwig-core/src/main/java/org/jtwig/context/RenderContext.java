package org.jtwig.context;

import org.jtwig.JtwigModel;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.model.Renderer;
import org.jtwig.context.model.ResourceContext;

public interface RenderContext {
    Configuration configuration();
    Renderer renderer();
    ResourceContext currentResource();
    JtwigModel model();
}
