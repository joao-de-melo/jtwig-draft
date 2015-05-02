package org.jtwig.parser.parboiled.expression;

import org.jtwig.model.expression.ConstantExpression;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

import java.math.BigDecimal;

public class ConstantExpressionParser extends ExpressionParser<ConstantExpression> {
    public ConstantExpressionParser(ParserContext context) {
        super(ConstantExpressionParser.class, context);
    }

    @Override
    public Rule ExpressionRule() {
        return FirstOf(
                TrueRule(),
                FalseRule(),
                NumberRule(),
                StringRule(),
                parserContext().parser(ComprehensionListExpressionParser.class).ExpressionRule(),
                parserContext().parser(EnumerationListExpressionParser.class).ExpressionRule()
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
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
            positionTrackerParser.PushPosition(),
            lexicParser.String(),
            swap(),
            push(new ConstantExpression(positionTrackerParser.pop(), lexicParser.pop()))
        );
    }

    public Rule TrueRule () {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
            positionTrackerParser.PushPosition(),
            lexicParser.Keyword(Keyword.TRUE),
            push(new ConstantExpression(positionTrackerParser.pop(), true))
        );
    }

    public Rule FalseRule () {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
            positionTrackerParser.PushPosition(),
            lexicParser.Keyword(Keyword.FALSE),
            push(new ConstantExpression(positionTrackerParser.pop(), false))
        );
    }
}
