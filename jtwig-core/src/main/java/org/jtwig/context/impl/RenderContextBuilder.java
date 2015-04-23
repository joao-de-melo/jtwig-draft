package org.jtwig.context.impl;

import org.apache.commons.lang3.builder.Builder;
import org.jtwig.JtwigModel;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.ResourceContext;
import org.jtwig.functions.resolver.FunctionResolver;
import org.jtwig.model.tree.Node;
import org.jtwig.util.JtwigValue;

import java.util.HashMap;
import java.util.Stack;

public class RenderContextBuilder implements Builder<RenderContext> {


    public static RenderContextBuilder renderContext() {
        return new RenderContextBuilder();
    }

    private RenderContextBuilder() {}

    private Configuration configuration;
    private JtwigModel model = new JtwigModel(new HashMap<String, JtwigValue>());

    public RenderContextBuilder withConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public RenderContextBuilder withModel(JtwigModel model) {
        this.model = model;
        return this;
    }

    @Override
    public RenderContext build() {
        JtwigModel model = this.model;
        Stack<JtwigModel> modelStack = new Stack<>();
        modelStack.push(model);
        Stack<ResourceContext> resourceContextStack = new Stack<>();
        Stack<Node> nodeContextStack = new Stack<>();
        return new CoreRenderContext(configuration, modelStack, resourceContextStack, nodeContextStack);
    }
}
