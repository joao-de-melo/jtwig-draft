package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.configuration.MathContextParameter;
import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class NegativeOperationCalculatorTest {
    private final RenderContext renderContext = mock(RenderContext.class, RETURNS_DEEP_STUBS);
    private final Position position = mock(Position.class);
    private NegativeOperationCalculator underTest = new NegativeOperationCalculator();

    @Before
    public void setUp() throws Exception {
        when(renderContext.configuration().parameter(MathContextParameter.mathContext())).thenReturn(MathContext.DECIMAL32);
    }

    @Test
    public void negative() throws Exception {
        Expression operand = mock(Expression.class);
        when(operand.calculate(renderContext)).thenReturn(new JtwigValue(new BigDecimal("2.0")));

        JtwigValue result = underTest.calculate(renderContext, position, operand);

        assertThat(result.asNumber(), is(new BigDecimal("-2.0")));
    }
}