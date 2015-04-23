package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

import java.math.BigDecimal;

public class ConstantExpressionParser extends ExpressionParser<ConstantExpression> {
    public ConstantExpressionParser(ParserContext context) {
        super(ConstantExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                NumberRule(),
                StringRule()
        );
    }


    public Rule NumberRule() {
        return Sequence(
                parserContext().parser(PositionTrackerParser.class).PushPosition(),
                parserContext().parser(LexicParser.class).Number(),
                push(new ConstantExpression(parserContext().parser(PositionTrackerParser.class).pop(), new BigDecimal(parserContext().parser(LexicParser.class).match())))
        );
    }

    public Rule StringRule() {
        return Sequence(
                parserContext().parser(PositionTrackerParser.class).PushPosition(),
                parserContext().parser(LexicParser.class).String(),
                swap(),
                push(new ConstantExpression(parserContext().parser(PositionTrackerParser.class).pop(), parserContext().parser(LexicParser.class).pop()))
        );
    }
}
