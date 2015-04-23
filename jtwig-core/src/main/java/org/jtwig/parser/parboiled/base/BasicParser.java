package org.jtwig.parser.parboiled.base;

import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.BaseParser;
import org.parboiled.MatcherContext;
import org.parboiled.errors.BasicParseError;
import org.parboiled.support.ValueStack;

import java.util.List;

public class BasicParser<T> extends BaseParser<T> {
    final ParserContext parserContext;

    public BasicParser (Class<? extends BasicParser> type, ParserContext context) {
        this.parserContext = context;
        this.parserContext.register(type, this);
    }

    public ParserContext parserContext() {
        return parserContext;
    }

    public void addError(String message) {
        MatcherContext matcherContext = (MatcherContext) getContext();
        matcherContext.markError();
        List parseErrors = matcherContext.getParseErrors();
        parseErrors.add(new BasicParseError(matcherContext.getInputBuffer(), parseErrors.size(), message));
    }



    // TODO: Remove
    @Deprecated
    public boolean printCurrentStack() {
        ValueStack valueStack = getContext().getValueStack();
        System.out.println("Printing stack");
        for (Object value : valueStack) {
            System.out.println(value);
        }
        return true;
    }
}
