package org.jtwig.context.impl;

import org.jtwig.JtwigModel;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.Renderer;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.model.tree.Node;
import org.jtwig.util.JtwigValue;

import java.util.HashMap;
import java.util.Stack;

class CoreRenderContext implements RenderContext {
    private final Configuration configuration;
    private final Stack<JtwigModel> modelStack;
    private final Stack<ResourceContext> resourceContextStack;
    private final Stack<Node> nodeContextStack;

    public CoreRenderContext(Configuration configuration,
                             Stack<JtwigModel> modelStack,
                             Stack<ResourceContext> resourceContextStack,
                             Stack<Node> nodeContextStack) {
        this.configuration = configuration;
        this.modelStack = modelStack;
        this.resourceContextStack = resourceContextStack;
        this.nodeContextStack = nodeContextStack;
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public Renderer renderer() {
        return new Renderer(this, modelStack, resourceContextStack, nodeContextStack, new JtwigModel(new HashMap<String, JtwigValue>()));
    }

    @Override
    public ResourceContext currentResource() {
        return resourceContextStack.peek();
    }

    @Override
    public JtwigModel model() {
        return modelStack.peek();
    }
}
