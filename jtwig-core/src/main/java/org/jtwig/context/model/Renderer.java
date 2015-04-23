package org.jtwig.context.model;

import org.jtwig.JtwigModel;
import org.jtwig.context.RenderContext;
import org.jtwig.model.tree.Node;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Renderer {
    private final RenderContext renderContext;
    private final Stack<JtwigModel> modelStack;
    private final Stack<ResourceContext> resourceContextStack;
    private final Stack<Node> nodeContextStack;
    private final JtwigModel model;
    private boolean inheritModel = true;

    public Renderer(RenderContext renderContext, Stack<JtwigModel> modelStack, Stack<ResourceContext> resourceContextStack, Stack<Node> nodeContextStack, JtwigModel model) {
        this.renderContext = renderContext;
        this.modelStack = modelStack;
        this.resourceContextStack = resourceContextStack;
        this.nodeContextStack = nodeContextStack;
        this.model = model;
    }

    public Renderer inheritModel(boolean inherit) {
        this.inheritModel = inherit;
        return this;
    }

    public Renderer define(String variableName, Object value) {
        model.define(variableName, value);
        return this;
    }

    public Renderer define(Map<Object, Object> map) {
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            model.define(entry.getKey().toString(), entry.getValue());
        }
        return this;
    }

    public ResourceRenderResult render(Resource resource) {
        ResourceContext resourceContext = new ResourceContext(resource, new HashMap<String, Macro>(), new HashMap<String, Renderable>());

        if (inheritModel) {
            model.merge(modelStack.peek());
        }

        resourceContextStack.push(resourceContext);
        modelStack.push(model);

        Renderable render = renderContext.configuration()
                .parser()
                .parse(resource)
                .render(renderContext);

        resourceContextStack.pop();
        modelStack.pop();

        return new ResourceRenderResult(resource, resourceContext, render);
    }

    public Renderable render (Node node) {
        nodeContextStack.push(node);
        model.merge(modelStack.peek());
        modelStack.push(model);
        Renderable render = node.render(renderContext);
        nodeContextStack.pop();
        modelStack.pop();
        return render;
    }
}
