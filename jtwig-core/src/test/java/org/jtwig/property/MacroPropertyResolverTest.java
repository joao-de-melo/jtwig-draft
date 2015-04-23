package org.jtwig.property;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.*;
import org.jtwig.functions.FunctionArgument;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.render.model.ByteArrayRenderable;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MacroPropertyResolverTest {
    private static final String MACRO_NAME = "macroName";
    private final Position position = mock(Position.class);
    private RenderContext renderContext = mock(RenderContext.class);
    private MacroPropertyResolver underTest = new MacroPropertyResolver() {
        @Override
        protected RenderContext getRenderContext() {
            return renderContext;
        }
    };
    private final ArrayList<FunctionArgument> arguments = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        arguments.clear();
    }

    @Test
    public void resolveWhenNotMacroContext() throws Exception {
        PropertyResolveRequest request = new PropertyResolveRequest(position, new Object(), MACRO_NAME, arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenEmptyMacroContext() throws Exception {
        HashMap<String, Macro> macros = new HashMap<>();
        MacroContext macroContext = new MacroContext(macros);
        PropertyResolveRequest request = new PropertyResolveRequest(position, macroContext, MACRO_NAME, arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void resolveWhenMacroContextContainsMacro() throws Exception {
        ArrayList<String> argumentNames = new ArrayList<>();
        HashMap<String, Macro> macros = new HashMap<>();
        MacroContext macroContext = new MacroContext(macros);
        Node content = mock(Node.class);
        Renderer renderer = mock(Renderer.class);

        macros.put(MACRO_NAME, new Macro(argumentNames, content));
        when(renderContext.renderer()).thenReturn(renderer);
        when(renderer.inheritModel(false)).thenReturn(renderer);
        when(renderer.define(anyString(), any())).thenReturn(renderer);
        when(renderer.render(content)).thenReturn(new ByteArrayRenderable("one".getBytes()));
        PropertyResolveRequest request = new PropertyResolveRequest(position, macroContext, MACRO_NAME, arguments);

        Optional<JtwigValue> result = underTest.resolve(request);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().asString(), is("one"));
    }
}