package org.jtwig.model.expression;

import com.google.common.base.Optional;
import org.jtwig.configuration.BooleanConfigurationParameters;
import org.jtwig.context.RenderContext;
import org.jtwig.exceptions.CalculationException;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VariableExpressionTest {
    private final Position position = mock(Position.class);
    private final String identifier = "variable";
    private final RenderContext context = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private VariableExpression underTest = new VariableExpression(position, identifier);

    @Test
    public void calculateWhenVariableDefined() throws Exception {
        JtwigValue value = new JtwigValue("one");
        when(context.valueContext().value(identifier)).thenReturn(Optional.of(value));

        JtwigValue result = underTest.calculate(context);

        assertThat(result, is(value));
    }

    @Test
    public void calculateWhenVariableUndefinedAndStrictModeDisabled() throws Exception {
        when(context.configuration().parameter(BooleanConfigurationParameters.STRICT_MODE)).thenReturn(false);
        when(context.valueContext().value(identifier)).thenReturn(Optional.<JtwigValue>absent());

        JtwigValue result = underTest.calculate(context);

        assertThat(result.isPresent(), is(false));
    }

    @Test(expected = CalculationException.class)
    public void calculateWhenVariableUndefinedAndStrictModeEnabled() throws Exception {
        when(context.configuration().parameter(BooleanConfigurationParameters.STRICT_MODE)).thenReturn(true);
        when(context.valueContext().value(identifier)).thenReturn(Optional.<JtwigValue>absent());

        underTest.calculate(context);
    }
}