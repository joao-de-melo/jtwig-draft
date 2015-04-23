package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.Node;
import org.jtwig.parser.parboiled.ParserContext;
import org.parboiled.Rule;

public abstract class AddonParser extends NodeParser<Node> {

    public AddonParser(Class type, ParserContext context) {
        super(type, context);
    }

    public Rule NodeRule () {
        int stackSizeBefore = getContext().getValueStack().size();
        return Sequence(
                Addon(),
                markErrorIfSizeDidNotGrowByOne(stackSizeBefore)
        );
    }

    boolean markErrorIfSizeDidNotGrowByOne(int stackSizeBefore) {
        if (getContext().getValueStack().size() != stackSizeBefore + 1) {
            addError(String.format("Addon is not pushing a node to the context as expected"));
        }
        return true;
    }

    abstract Rule Addon ();

}
