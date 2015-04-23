package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.util.ArrayList;
import java.util.Collection;

public class ListExpression extends Expression {
    private final Collection<Expression> expressions;

    public ListExpression(Position position, Collection<Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    public Collection<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Collection<Object> resolved = new ArrayList<>();
        for (Expression expression : expressions) {
            resolved.add(expression.calculate(context).asObject());
        }
        return new JtwigValue(resolved);
    }
}
