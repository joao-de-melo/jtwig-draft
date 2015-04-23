package org.jtwig.model.expression.operation.calculators.binary;

import org.jtwig.context.RenderContext;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

public class ConcatOperationCalculator implements BinaryOperationCalculator {
    @Override
    public JtwigValue calculate(RenderContext context, Position position, Expression leftOperand, Expression rightOperand) {
        return new JtwigValue(new StringBuilder()
                .append(leftOperand.calculate(context).asString())
                .append(rightOperand.calculate(context).asString())
                .toString());
    }
}
