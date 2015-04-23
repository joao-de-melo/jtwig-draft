package org.jtwig.model.tree;

import com.google.common.base.Optional;
import org.jtwig.context.model.Renderer;
import org.jtwig.context.model.ResourceRenderResult;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.include.IncludeConfiguration;
import org.jtwig.render.Renderable;
import org.jtwig.resource.Resource;
import org.jtwig.resource.ResourceNotFoundException;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class EmbedNodeTest extends AbstractNodeTest {
    private final Position position = mock(Position.class);
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final Expression includeExpression = mock(Expression.class);
    private final Expression mapExpression = mock(Expression.class);
    private final IncludeConfiguration includeConfiguration = mock(IncludeConfiguration.class, RETURNS_DEEP_STUBS);
    private final Renderer renderer = renderContext().renderer();
    private final Resource resource = mock(Resource.class);
    private EmbedNode underTest = new EmbedNode(position, nodes, includeConfiguration);

    @Before
    public void setUp() throws Exception {
        nodes.clear();
    }

    @Test
    public void renderWithNonExistingResourceWhenIgnoreMissing() throws Exception {
        when(includeConfiguration.isIgnoreMissing()).thenReturn(true);
        when(includeExpression.calculate(renderContext())).thenReturn(new JtwigValue("test"));
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.<Resource>absent());

        Renderable result = underTest.render(renderContext());

        assertThat(renderResult(result), is(""));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void renderWithNonExistingResourceWhenNotIgnoringMissing() throws Exception {
        when(includeExpression.calculate(renderContext())).thenReturn(new JtwigValue("test"));
        when(includeConfiguration.getInclude()).thenReturn(includeExpression);
        when(includeConfiguration.isIgnoreMissing()).thenReturn(false);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.<Resource>absent());
        when(renderer.inheritModel(anyBoolean())).thenReturn(renderer);
        when(renderer.define(anyMap())).thenReturn(renderer);

        underTest.render(renderContext());
    }

    @Test
    public void renderWithNodes() throws Exception {
        Node node = mock(Node.class);
        nodes.add(node);
        Resource resource = mock(Resource.class);
        Renderable renderable = mock(Renderable.class);
        ResourceRenderResult renderResult = mock(ResourceRenderResult.class);

        when(includeExpression.calculate(renderContext())).thenReturn(new JtwigValue("test"));
        when(includeConfiguration.isIgnoreMissing()).thenReturn(false);
        when(renderContext().configuration().resourceResolver().resolve(any(Resource.class), anyString())).thenReturn(Optional.of(resource));
        when(renderer.inheritModel(anyBoolean())).thenReturn(renderer);
        when(renderer.define(anyMap())).thenReturn(renderer);
        when(renderer.render(resource)).thenReturn(renderResult);
        when(renderResult.renderable()).thenReturn(renderable);

        Renderable result = underTest.render(renderContext());

        verify(renderer).render(node);
        assertThat(result, is(renderable));
    }
}