package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.ImportNode;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.expression.VariableExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.parboiled.Rule;

public class ImportNodeParser extends NodeParser<ImportNode> {
    public ImportNodeParser(ParserContext context) {
        super(ImportNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        VariableExpressionParser variableExpressionParser = parserContext().parser(VariableExpressionParser.class);
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(), spacingParser.Spacing(),
                lexicParser.Keyword(Keyword.IMPORT),
                spacingParser.Spacing(),
                anyExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                String("as"), spacingParser.Spacing(),
                variableExpressionParser.ExpressionRule(),
                spacingParser.Spacing(),
                limitsParser.endCode(),
                push(new ImportNode(
                        positionTrackerParser.pop(2),
                        anyExpressionParser.pop(1),
                        variableExpressionParser.pop()
                ))
        );
    }
}
