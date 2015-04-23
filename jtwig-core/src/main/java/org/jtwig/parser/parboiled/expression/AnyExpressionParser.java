package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.Expression;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public class AnyExpressionParser extends ExpressionParser<Expression> {
    public AnyExpressionParser(ParserContext context) {
        super(AnyExpressionParser.class, context);
    }

    public Rule ExpressionRule() {
        return FirstOf(
                parserContext().parser(TernaryOperationExpressionParser.class).ExpressionRule(),
                parserContext().parser(BinaryOperationExpressionParser.class).ExpressionRule(),
                parserContext().parser(PrimaryExpressionParser.class).ExpressionRule()
        );
    }
}
