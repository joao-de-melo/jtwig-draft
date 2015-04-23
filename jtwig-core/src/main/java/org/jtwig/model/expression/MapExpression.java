package org.jtwig.model.expression;

import org.jtwig.context.RenderContext;
import org.jtwig.model.position.Position;
import org.jtwig.util.JtwigValue;

import java.util.HashMap;
import java.util.Map;

public class MapExpression extends Expression {
    private final Map<Expression, Expression> expressions;

    public MapExpression(Position position, Map<Expression, Expression> expressions) {
        super(position);
        this.expressions = expressions;
    }

    public Map<Expression, Expression> getExpressions() {
        return expressions;
    }

    @Override
    public JtwigValue calculate(RenderContext context) {
        Map<Object, Object> result = new HashMap<>();
        for (Map.Entry<Expression, Expression> entry : expressions.entrySet()) {
            result.put(entry.getKey().calculate(context).asObject(), entry.getValue().calculate(context).asObject());
        }
        return new JtwigValue(result);
    }
}
