package org.jtwig.context.model;

import org.jtwig.JtwigModel;
import org.jtwig.configuration.Configuration;
import org.jtwig.context.RenderContext;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.JtwigParser;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Stack;

import static junit.framework.Assert.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class RendererTest {
    private final RenderContext renderContext = mock(RenderContext.class);
    private final Stack<JtwigModel> modelStack = new Stack<>();
    private final Stack<ResourceContext> contextStack = new Stack<>();
    private final Stack<Node> nodeStack = new Stack<>();
    private final JtwigModel firstModel = mock(JtwigModel.class);
    private final JtwigModel model = mock(JtwigModel.class);
    private Renderer underTest = new Renderer(renderContext, modelStack, contextStack, nodeStack, model);

    @Before
    public void setUp() throws Exception {
        modelStack.clear();
        contextStack.clear();
        nodeStack.clear();
        modelStack.push(firstModel);
    }

    @Test
    public void defineVariable() throws Exception {
        Object value = new Object();

        underTest.define("one", value);

        verify(model).define("one", value);
    }

    @Test
    public void defineMap() throws Exception {
        Object value = new Object();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("one", value);
        map.put("one1", value);

        underTest.define(map);

        verify(model).define("one", value);
        verify(model).define("one1", value);
    }

    @Test
    public void renderNode() throws Exception {
        Node node = mock(Node.class);
        Renderable renderable = mock(Renderable.class);
        when(node.render(renderContext)).thenReturn(renderable);

        Renderable result = underTest.render(node);

        assertSame(result, renderable);
        verify(model).merge(firstModel);
    }

    @Test
    public void renderResourceWhenInheritModel() throws Exception {
        underTest.inheritModel(true);
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        Configuration configuration = mock(Configuration.class);
        when(renderContext.configuration()).thenReturn(configuration);
        JtwigParser jtwigParser = mock(JtwigParser.class);
        when(configuration.parser()).thenReturn(jtwigParser);
        when(jtwigParser.parse(resource)).thenReturn(node);
        when(node.render(renderContext)).thenReturn(renderable);

        ResourceRenderResult result = underTest.render(resource);

        verify(model).merge(firstModel);
        assertThat(result.renderable(), is(renderable));
        assertThat(result.resource(), is(resource));
    }

    @Test
    public void renderResourceWhenNotInheritModel() throws Exception {
        underTest.inheritModel(false);
        Node node = mock(Node.class);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        Configuration configuration = mock(Configuration.class);
        when(renderContext.configuration()).thenReturn(configuration);
        JtwigParser jtwigParser = mock(JtwigParser.class);
        when(configuration.parser()).thenReturn(jtwigParser);
        when(jtwigParser.parse(resource)).thenReturn(node);
        when(node.render(renderContext)).thenReturn(renderable);

        ResourceRenderResult result = underTest.render(resource);

        verify(model, never()).merge(firstModel);
        assertThat(result.renderable(), is(renderable));
        assertThat(result.resource(), is(resource));
    }
}