package org.jtwig.model.expression.operation.calculators.unary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

public interface UnaryOperationCalculator {
    JtwigValue calculate (RenderContext context, Position position, Expression operand);
}
