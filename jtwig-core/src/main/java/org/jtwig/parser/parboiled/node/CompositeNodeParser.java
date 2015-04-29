package org.jtwig.parser.parboiled.node;

import org.jtwig.model.tree.CompositeNode;
import org.jtwig.model.tree.Node;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.BasicParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.parboiled.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.parboiled.Parboiled.createParser;

public class CompositeNodeParser extends NodeParser<CompositeNode> {
    public CompositeNodeParser(ParserContext context) {
        super(CompositeNodeParser.class, context);
        createParser(NodeListParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        NodeListParser nodeListParser = parserContext().parser(NodeListParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                nodeListParser.List(),
                push(new CompositeNode(
                        positionTrackerParser.pop(1),
                        nodeListParser.pop()
                ))
        );
    }

    public static class NodeListParser extends BasicParser<Collection<Node>> {
        final Collection<Class> contentParsers;

        public NodeListParser(ParserContext context) {
            super(NodeListParser.class, context);

            contentParsers = Arrays.<Class>asList(
                    BlockNodeParser.class,
                    EmbedNodeParser.class,
                    ForLoopNodeParser.class,
                    IfNodeParser.class,
                    ImportNodeParser.class,
                    IncludeNodeParser.class,
                    MacroNodeParser.class,
                    OutputNodeParser.class,
                    SetNodeParser.class,
                    TextNodeParser.class
            );
        }

        Rule List() {
            Rule[] rules = new Rule[contentParsers.size()];
            int i = 0;
            for (Class contentParser : contentParsers) {
                NodeParser<Node> parser = (NodeParser<Node>) parserContext().parser(contentParser);
                rules[i++] = Sequence(
                        parser.NodeRule(),
                        peek(1).add(parser.pop())
                );
            }
            return Sequence(
                    push(new ArrayList<Node>()),

                    ZeroOrMore(
                            FirstOf(
                                    FirstOf(rules),
                                    parserContext().parser(CommentParser.class).Comment()
                            )
                    )
            );
        }


    }
}
